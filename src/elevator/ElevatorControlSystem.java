package elevator;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ElevatorControlSystem {

    private BlockingDeque<MoveRequest> upRequests = new LinkedBlockingDeque<MoveRequest>();

    private BlockingDeque<MoveRequest> downRequests = new LinkedBlockingDeque<MoveRequest>();

    private List<Elevator> elevators = null;

    private int MAX_FLOOR = 0;

    public ElevatorControlSystem(int MAX_FLOOR, int MAX_ELEVATORS) {
        this.MAX_FLOOR = MAX_FLOOR;
        this.elevators = new ArrayList<Elevator>(2);
        this.elevators.add(new Elevator(1, MAX_FLOOR));
        this.elevators.add(new Elevator(2, MAX_FLOOR));

    }


    public void startElevators() {
        new Thread(this.elevators.get(0)).start();
        new Thread(this.elevators.get(1)).start();
    }

    public void placeRequest(MoveRequest request) {
        if (request.getDirection().equals(STATE.UP)) {
            upRequests.add(request);
        } else if (request.getDirection().equals(STATE.DOWN)) {
            downRequests.add(request);
        }
    }

    private class Elevator implements Runnable {

        private int num;

        private STATE state = STATE.IDLE;

        private int floor = 0;

        private int MAX_FLOOR = 0;

        private volatile boolean isStopRequested = false;

        private Set<Integer> stops = new HashSet<Integer>();

        public Elevator(int num, int MAX_FLOOR) {
            this.MAX_FLOOR = MAX_FLOOR;
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("Elevator will run");
            while (true) {
                try {
                    //consume from the queue thats has same direction
                    if (state.equals(STATE.IDLE)) {
                        tryToBeUseful();
                    } else if (state.equals(STATE.DOWN) || floor == MAX_FLOOR) {
                        serviceDownRequests();
                    } else if (state.equals(STATE.UP) || floor == 0) {
                        serviceUpRequests();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void tryToBeUseful() throws InterruptedException {
            if (!downRequests.isEmpty()) {
                addIntermediateStop(downRequests);

            }
            if (!upRequests.isEmpty()) {
                addIntermediateStop(upRequests);

            }

        }

        private void addIntermediateStop(BlockingDeque<MoveRequest> requests) {
            MoveRequest request = requests.peek();
            if (request.getFromFloor() != floor) {
                stops.add(request.getFromFloor());
                flipState(request);
            } else {
                state = request.getDirection();
            }
        }

        private void flipState(MoveRequest request) {
            if (request.getFromFloor() > floor) {
                state = STATE.UP;
            } else {
                state = STATE.DOWN;
            }
        }

        private void serviceDownRequests() throws InterruptedException {
            Set<MoveRequest> nonServiceableRequests = new HashSet<MoveRequest>();
            while (!downRequests.isEmpty()) {
                MoveRequest request = downRequests.take();
                if (request.getFromFloor() <= floor) {
                    if (floor != request.getFromFloor()) {
                        stops.add(request.getFromFloor());
                    }
                    stops.add(request.getToFloor());
                } else {
                    nonServiceableRequests.add(request);
                }
            }
            downRequests.addAll(nonServiceableRequests);
            if (!stops.isEmpty()) {
                moveDown();
                if (stops.contains(floor) || floor == 0) {
                    halt();
                }
            }
        }


        private void serviceUpRequests() throws InterruptedException {
            Set<MoveRequest> nonServiceableRequests = new HashSet<MoveRequest>();
            while (!upRequests.isEmpty()) {
                MoveRequest request = upRequests.take();
                if (request.getFromFloor() >= floor) {
                    if (floor != request.getFromFloor()) {
                        stops.add(request.getFromFloor());
                    }
                    stops.add(request.getToFloor());
                } else {
                    nonServiceableRequests.add(request);
                }
            }
            upRequests.addAll(nonServiceableRequests);
            if (!stops.isEmpty()) {
                moveUp();
                if (stops.contains(floor) || floor == MAX_FLOOR) {
                    halt();
                }
            } else {
                state = STATE.IDLE;
            }
        }

        private void halt() {
            isStopRequested = true;
            stops.remove(floor);
            //take user request
            //stops.add(1);
            isStopRequested = false;
        }

        private void moveDown() {
            floor--;
            state = STATE.DOWN;
            System.out.println("Elevator " + num + " going down 1 floor to:" + floor + ", stops=" + stops);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void moveUp() {
            floor++;
            state = STATE.UP;
            System.out.println("Elevator " + num + " going up 1 floor to:" + floor + ", stops=" + stops);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}
