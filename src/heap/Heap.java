package heap;

public class Heap {


    private int[] arr;

    private int size = 0;

    private enum Type {Max, Min;}

    private Type type;

    public int peek() {
        return arr[0];
    }

    public int poll() throws Exception {
        if (size == 0) {
            throw new Exception("heap empty");
        }
        int ret = arr[0];
        if (type == Type.Max) {
            arr[0] = Integer.MIN_VALUE;
            maxHeapify(arr, 0);
        } else {
            arr[0] = Integer.MAX_VALUE;
            minHeapify(arr, 0);

        }
        size--;
        return ret;
    }

    public Heap(int[] arr, Type inType) {
        this.arr = arr;
        size = arr.length;
        type = inType;
        int index = arr.length / 2 - 1;
        for (int i = index; i >= 0; i--) {
            if (type == Type.Max) {
                maxHeapify(arr, i);
            } else {
                minHeapify(arr, i);
            }

        }

    }

    private void maxHeapify(int[] arr, int index) {

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        int largest = (left < arr.length && arr[left] > arr[index]) ? left : index;
        largest = (right < arr.length && arr[right] > arr[largest]) ? right : largest;

        if (largest != index) {
            swap(arr, index, largest);
            maxHeapify(arr, largest);
        }


    }

    private void minHeapify(int[] arr, int index) {

        int left = 2 * index + 1;
        int right = 2 * index + 2;

        int smallest = (left < arr.length && arr[left] < arr[index]) ? left : index;
        smallest = (right < arr.length && arr[right] < arr[smallest]) ? right : smallest;

        if (smallest != index) {
            swap(arr, index, smallest);
            minHeapify(arr, smallest);
        }


    }

    private void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public static void main(String[] args) throws Exception {

        //Heap maxHeap = new Heap(new int[]{7, 15, 3, 9, 2, 12, 6, 10}, Type.Max);
        Heap minHeap = new Heap(new int[]{7, 15, 3, 9, 2, 12, 6, 10}, Type.Min);
        Heap maxHeap = new Heap(new int[]{7, 15, 3}, Type.Max);
        System.out.println("Min = " + minHeap.poll());
        System.out.println("Min = " + minHeap.poll());
        System.out.println("Min = " + minHeap.poll());
        System.out.println("Max = " + maxHeap.poll());
        System.out.println("Max = " + maxHeap.poll());
        System.out.println("Max = " + maxHeap.poll());
        System.out.println("Max = " + maxHeap.poll());

    }
}
