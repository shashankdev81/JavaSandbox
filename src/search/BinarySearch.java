package search;

public class BinarySearch {


    public static void main(String[] args) {
        int[] arr = {2, 4, 6, 8, 3, 1};
        //int[] arr = {2, 8, 6, 5, 4, 3, 1};
        int target = 8;
        int max = bitonicIndexSearch(arr, 0, arr.length - 1);
        System.out.println("max=" + max);
        int search = binarySearch(arr, 0, max, target, true);
        System.out.println("first half search=" + search);
        if (search == -1) {
            System.out.println("second half search=" + binarySearch(arr, max + 1, arr.length - 1, target, false));
        }

    }

    private static int bitonicIndexSearch(int[] arr, int low, int high) {
        if (low == high || (high - low == 1 && arr[low] < arr[high])) {
            return low;
        } else if ((high - low == 1 && arr[high] < arr[low])) {
            return high;
        } else {
            int mid = (low + high) / 2;
            if (arr[mid - 1] < arr[mid] && arr[mid] < arr[mid + 1]) {
                return bitonicIndexSearch(arr, mid + 1, high);
            } else if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                return mid;
            } else {
                return bitonicIndexSearch(arr, low, mid - 1);
            }
        }
    }


    private static int binarySearch(int[] arr, int low, int high, int target, boolean isAsc) {
        if (low > high) return -1;
        else if (low == high) return low;
        int mid = (low + high) / 2;
        if ((isAsc && arr[mid] < target) || (!isAsc && arr[mid] > target)) {
            return binarySearch(arr, mid + 1, high, target, isAsc);
        } else if ((isAsc && arr[mid] > target) || (!isAsc && arr[mid] < target)) {
            return binarySearch(arr, low, mid - 1, target, isAsc);
        } else {
            return mid;
        }
    }
}
