
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;


public class algorithms {
	private static int[] unsorted;
    private static List<Integer> list = new ArrayList<Integer>();
	public static void main(String[] args) {
		
		Scanner userInput = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);

		//Menu for sorting algorithm experiment

        System.out.println("Please select a sorting algorithm for the experiment!\n"
                + "1) Insertion Sort\n"
                + "2) Merge Sort\n"
                + "3) Quick Sort (pivot is always selected as the first element)\n"
                + "4) Partial Selection Sort\n"
                + "5) Partial Heap Sort (with median-of-three pivot selection)\n"
                + "6) Quick Select (pivot is always selected as the first element)\n"
                + "7) Quick Select (with median-of-three pivot selection)\n"
                + "8) Exit\n");
        System.out.print("Enter the number: ");
        int algorithm = userInput.nextInt();
        int k=0;
        if(algorithm == 4 || algorithm == 6 || algorithm == 5){
            System.out.print("Enter k: ");
             k = userInput.nextInt();
        }

            while (algorithm <= 0 || algorithm > 8) {
                System.out.println("You entered invalid option! Select again!");
                System.out.print("Enter the number: ");
                algorithm = userInput.nextInt();
            }
            //Prompt user for file
            System.out.print("Enter the file name (without extension): ");
            String fileName = input2.nextLine();
            File sourcefile = new File("inputs\\" + fileName + ".txt");


            while (!sourcefile.exists()) {
                System.out.println("Source file does not exist!");
                System.out.print("Enter the file name again: ");
                fileName = input2.nextLine();
                sourcefile = new File("inputs\\" + fileName + ".txt");
            }

            // Create a Scanner for the file
            Scanner input;
            int number = 0;
            try {
                input = new Scanner(sourcefile);

                while (input.hasNext()) {
                    String str = input.next();
                    if (str.contains(",")) {
                        String[] arr = str.split(",");
                        unsorted = new int[arr.length];

                        for (int i = 0; i < arr.length; i++) {
                            unsorted[i] = Integer.parseInt(arr[i]);
                        }
                        for (int i : unsorted) {
                            number = number * 10 + i;
                        }

                        list.add(number);
                        number = 0;
                    }
                }
                int[] array = new int[list.size()];
                array = list.stream().mapToInt(Integer::intValue).toArray();


                //measuring time - start point
                long nano_startTime = System.nanoTime();


                //It works the sorting type based on option selection from the menu
                if (algorithm == 1) {
                    insertionSort(array);
                }else if(algorithm == 2) {
                    mergeSort(array,0,array.length-1);
                } else if (algorithm == 3) {
                    quickSortFirst(array, 0, array.length - 1);
                } else if (algorithm == 4) {
                    partialSelectionSort(array,k);
                } else if (algorithm == 5) {
                    partialHeapSort(array,k);
                } else if (algorithm == 6) {
                    System.out.println(quickSelectFirst(array,0,array.length-1,k));
                } else if (algorithm == 7) {
                    //quickSelectSecond();
                } else {
                    System.exit(1);
                }

                //measuring the time - end point
                long nano_endTime = System.nanoTime();

                //finding the time difference
                System.out.println("Time taken in nano seconds: "
                        + (nano_endTime - nano_startTime));


                //After the sorting, it prints the sorted array
                //printArray(array);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    static int partitionQuickSelect(int[]arr,int low,int high){
        int pivot = arr[high], pivotloc = low;
        for (int i = low; i <= high; i++) {
            // inserting elements of less value
            // to the left of the pivot location
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pivotloc];
                arr[pivotloc] = temp;
                pivotloc++;
            }
        }

        // swapping pivot to the final pivot location
        int temp = arr[high];
        arr[high] = arr[pivotloc];
        arr[pivotloc] = temp;

        return pivotloc;
    }
    static int quickSelectFirst(int[] arr,int low,int high,int k){
        int partition = partitionQuickSelect(arr,low,high);
        if(partition == k-1)
            return arr[partition];
        else if (partition<k-1)
            return quickSelectFirst(arr,partition+1,high,k);
        else
            return quickSelectFirst(arr,low,partition-1,k);

    }

    static void partialSelectionSort(int[] array,int k){
        for (int i = 0;i<k;i++){
            int minIndex = i;
            int minValue = array[i];
            for(int j = i+1;j<array.length;j++){
                if(array[j]<minValue){
                    minIndex = j;
                    minValue = array[j];
                }
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }

    }
	static void insertionSort(int[] array) { //Method for Insertion Sort
        int n = array.length;
        //for key selection
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            //Compare the key and elements of array, move greater elements to one position further  
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;

            }
            array[j + 1] = key;
        }

    }


    static void merge(int[] array, int l, int x, int r) { //Method of merging for Merge Sort
        //Find sizes of two sub arrays
        int arrSize1 = x - l + 1;
        int arrSize2 = r - x;

        //Create temp arrays 
        int[] tempArray1 = new int[arrSize1];
        int[] tempArray2 = new int[arrSize2];

        // Copy elements to temp arrays
        for (int i = 0; i < arrSize1; ++i)
        	tempArray1[i] = array[l + i];
        for (int j = 0; j < arrSize2; ++j)
        	tempArray2[j] = array[x + 1 + j];

        // Merge the temp arrays

        // Initial the indexes of two arrays
        int i = 0, j = 0;

        int k = l;
        while (i < arrSize1 && j < arrSize2) {

            if (tempArray1[i] <= tempArray2[j]) {
                array[k] = tempArray1[i];
                i++;
            }
            else {
                array[k] = tempArray2[j];
                j++;
            }
            k++;
        }

        // If there are any elements left, copy those elements of tempArray1
        while (i < arrSize1) {
            array[k] = tempArray1[i];
            i++;
            k++;
        }

        // If there are any elements left, copy those elements of tempArray2
        while (j < arrSize2) {
            array[k] = tempArray2[j];
            j++;
            k++;
        }

    }

    static void mergeSort(int[] array, int start, int end) { //Method for Merge Sort
        if (start < end) {
            // Find the middle point
            int middle = start + (end - start)/2;

            // Sort first and second parts
            mergeSort(array, start, middle);
            mergeSort(array, middle + 1, end);

            // Merge the sorted parts
            merge(array, start, middle, end);

        }

    }
    
    static void quickSortFirst(int[] array, int left, int right) { //Method for Quick Sort by selecting first element as pivot
        if (left < right) {
            //Selecting first element as pivot
            int pivot = array[left];
            //For partition
            int i = left;
            int j = right;
            while (i < j) {
                //Shift one place to past pivot element
                i += 1;
                //Search right part to find elements greater than pivot
                while (i <= right && array[i] < pivot) {
                    i += 1;

                }
                //Search left part to find elements smaller than pivot
                while (j >= left && array[j] > pivot) {
                    j -= 1;

                }
                if (i <= right && i < j) {
                    //Swap
                    swap(array, i, j);

                }
            }
            //Place pivot in correct place
            swap(array, left, j);
            //Sorting again for partition parts
            quickSortFirst(array, left, j - 1);
            quickSortFirst(array, j + 1, right);
        }
    }
    
    static void swap(int[] array, int i, int j) { //Swap function for quickSortFirst
        if (i >= 0 && j >= 0 && i < array.length && j < array.length) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public static int medianPivot(int array[], int first, int last) { //Find the median pivot for Quick Sort with median of three
        
    	//Sort array for first, last and middle elements and use the second largest element as pivot
 
        int mid = (last) / 2;

        int[] sortingArr = {array[first], array[mid], array[last]};
        Arrays.sort(sortingArr);

        //System.out.println("\tMiddle of Arr at Index= " + mid + " : " + array[mid]);
        int middleValue = sortingArr[1];

        //System.out.println("\t"+Arrays.toString(sortingArr));
        //Swap with the last for pivot
        	int temp = array[last];
        	array[last] = middleValue;
        if (middleValue == array[first]) {
            array[first] = temp;
        } else if (middleValue == array[mid]) {
            array[mid] = temp;
        }
        return partition(array, first, last);
    }
 
    public static void medianQuickSort(int arr[], int first, int last) { //Method for Quick Sort with median-of-three pivot selection
        if (first >= last)
            return;

        if (first < last) {

            int pivot = medianPivot(arr, first, last);
            //System.out.println(pivot);
            QuickSort(arr, first, last);
        }
    }
    
    public static void QuickSort(int arr[], int low, int high) { //Method for Quick Sort with median-of-three pivot selection

        if (low < high) {
            int pivot = partition(arr, low, high);

            //Sort elements before and after partition
            QuickSort(arr, low, pivot - 1);
            QuickSort(arr, pivot + 1, high);
        }
    }
    public static int partition(int array[], int first, int last) { //Partition for Quick Sort of median-of-three
        int pivot = array[last];
        int i = (first - 1);

        for (int j = first; j < last; j++) {
        	
            // If current element is smaller than or equal to pivot
            if (array[j] <= pivot) {
                i++;

                //Swap
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // swap for pivot
        int temp = array[i + 1];
        array[i + 1] = array[last];
        array[last] = temp;

        return i + 1;
    }


    public static void countingSort(int[] elements) { //Method for Counting Sort
        int maxValue = findMax(elements);
        int[] counts = new int[maxValue + 1];

        // Counting elements
        for (int i = 0; i < elements.length; i++) {
            counts[elements[i]]++;
        }

        // Writes results back
        int place = 0;
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[i]; j++) {
                elements[place++] = i;
            }
        }
    }

    public static int findMax(int[] elements) { //Finding maximum for Counting Sort
        int max = 0;
        for (int i = 0; i < elements.length; i++) {
            int element = elements[i];

            if (element > max) {
                max = element;
            }
        }
        return max;
    }
    
    static void printArray(int arr[]) { //Printing the sorted arrays
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        	System.out.println();
    }
    public static void partialHeapSort(int[] arr, int k) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap for getting k'th element for k times
        for (int i = n - 1; i > n-k; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);

        }
        System.out.println(arr[0] + " is the "+ k +". biggest element in the list.");
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    static void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
