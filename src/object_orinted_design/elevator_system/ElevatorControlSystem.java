package object_orinted_design.elevator_system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ElevatorControlSystem {

    private Elevator[] elevators;

    private PriorityQueue<Floor> upQueue;

    private PriorityQueue<Floor> downQueue;

    private ScheduledExecutorService service1;

    private ExecutorService service2;

    public ElevatorControlSystem withElevators(int floors, int elevatorsCount, int capacity) {
        service1 = Executors.newScheduledThreadPool(1);
        service2 = Executors.newFixedThreadPool(elevatorsCount);
        this.elevators = new Elevator[elevatorsCount];
        for (int i = 0; i < elevators.length; i++) {
            elevators[i] = new Elevator(floors, capacity);
        }
        //requestMap = new HashMap<>();
        upQueue = new PriorityQueue<>(Comparator.comparingInt(Floor::getValue));
        downQueue = new PriorityQueue<>(Comparator.comparingInt(Floor::getValue).reversed());
        service1.scheduleAtFixedRate(() -> {
            processQueue(upQueue, Direction.UP);
            processQueue(downQueue, Direction.DOWN);
        }, 1, 1, TimeUnit.SECONDS);
        return this;
    }

    private void processQueue(PriorityQueue<Floor> queue, Direction direction) {
        if (!queue.isEmpty()) {
            List<Elevator> lifts = Arrays.stream(elevators)
                .filter(e -> e.getDirection() == Direction.STATIONARY
                    || (direction == Direction.UP && e.getCurrFloor() < queue.peek().getValue())
                    || (direction == Direction.DOWN && e.getCurrFloor() > queue.peek().getValue()))
                .collect(Collectors.toList());

            if (!lifts.isEmpty()) {
                Elevator elevator = lifts.get(0);
                if (elevator.getDirection() == Direction.STATIONARY) {
                    service2.submit(() -> {
                        lifts.get(0).move(direction, queue.poll());
                    });
                } else {
                    lifts.get(0).setDestination(queue.poll());
                }
            }
        }
    }


    public void requestLift(Floor floor, Direction liftRequest) {
        if (liftRequest.equals(Direction.UP)) {
            upQueue.offer(floor);
        } else {
            downQueue.offer(floor);
        }
    }
}
