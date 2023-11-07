package concurrency.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class FileSumCalculator {

    private ExecutorService service = Executors.newFixedThreadPool(10);

    private int MAX_MACHINES = Integer.MAX_VALUE;

    public long getTotalSum(int files) {

        long total = 0;

        List<Integer> fileIds = new ArrayList<Integer>();
        for (int i = 0; i < files; i++) {
            fileIds.add(i);
        }

        int machineId = 0;
        while (!fileIds.isEmpty()) {

            List<Future<Long>> results = new ArrayList<Future<Long>>();
            Map<Future, Integer> outcomesMap = new HashMap<Future, Integer>();
            List<Integer> failures = new ArrayList<Integer>();

            for (Integer fid : fileIds) {
                Callable task = new PerFileSum(fid, machineId++);
                if (machineId == MAX_MACHINES) {
                    machineId = 0;
                }
                Future<Long> outcome = service.submit(task);
                results.add(outcome);
                outcomesMap.put(outcome, fid);

            }

            for (Future<Long> f : results) {
                long res = -1;
                try {
                    res = f.get();
                } catch (InterruptedException e) {
                    //do nothing
                } catch (ExecutionException e) {
                    //do nothing
                }
                if (res != -1) {
                    total += res;
                } else {
                    failures.add(outcomesMap.get(f));
                }
            }
            fileIds = failures;
        }

        return total;

    }


    private class PerFileSum implements Callable<Long> {

        private int fileId;
        private int machineId;

        public PerFileSum(int fid, int mid) {
            this.fileId = fid;
            this.machineId = mid;
        }

        public Long call() {
            long result = -1;
            try {
                result = total(fileId, machineId);
            } catch (Exception bme) {
                //do nothing
            }
            return result;
        }

        private long total(int fileId, int machineId) throws Exception {
            long res = (long) Math.random() * 100;
            if (res == 50) {
                throw new Exception("BadMachineException");
            }
            return res;
        }
    }

}
