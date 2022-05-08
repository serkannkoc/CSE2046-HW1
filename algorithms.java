
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;


public class algorithms {
    static int count = 1;
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
        if(algorithm != 8 ){
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
                ArrayList<Integer> integerArray = new ArrayList<>(array.length);
                if(algorithm == 6 || algorithm == 7) {

                    for (int i : array) {
                        integerArray.add(i);
                    }
                }

                //measuring time - start point
                long nano_startTime = System.nanoTime();


                //It works the sorting type based on option selection from the menu
                if (algorithm == 1) {
                    insertionSort(array);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +array[k-1]);
                }else if(algorithm == 2) {
                    mergeSort(array,0,array.length-1);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +array[k-1]);
                } else if (algorithm == 3) {
                    quickSort(array, 0, array.length - 1);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +array[k-1]);

                } else if (algorithm == 4) {
                    partialSelectionSort(array,k);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +array[k-1]);
                } else if (algorithm == 5) {
                    partialHeapSort(array,k);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +array[0]);
                } else if (algorithm == 6) {
                   int m = quickSelectFirst(integerArray,k);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +m);
                } else if (algorithm == 7) {
                    int n = quickSelectSecond(integerArray,0,integerArray.size()-1,k);
                    System.out.print("Time complexity is: ");
                    System.out.println(count + " basic operations.");
                    System.out.println("The K'th element is: " +n);
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




    static int partitionQuickSelect(ArrayList arr,int low,int high){
        int pivot = (int)arr.get(high), pivotloc = low;
        for (int i = low; i <= high; i++) {
            count++;
            if ((int)arr.get(i) < pivot) {

                int temp = (int)arr.get(i);
                arr.set(i,arr.get(pivotloc));
                arr.set(pivotloc,temp);
                pivotloc++;
            }

        }
        int temp = (int)arr.get(high);
        arr.set(high,arr.get(pivotloc));
        arr.set(pivotloc,temp);

        return pivotloc;
    }


    static int quickSelectSecond(ArrayList array,int left,int right,int k){
        if(k>0 && k<= right-left+1){
            int index = partitionQuickSelect(array,left,right);
                    if(index-1==k-1)
                        return (int)array.get(index);
                    if(index-1 > k-1)
                        return quickSelectSecond(array,1,index-1,k);
                    return quickSelectSecond(array,index+1,right,k-index+left-1);

        }
        return -1;
    }


    static int quickSelectFirst(ArrayList arr,int k){

        int pivot = (int) arr.get(0);
        ArrayList arr1 = new ArrayList();
        ArrayList arr2 = new ArrayList();
        for(int i = 1;i<arr.size();i++){
            count++;
            if ((int)arr.get(i)<pivot){
                arr1.add((int)arr.get(i));
            }else if ((int)arr.get(i)>pivot){
                arr2.add((int)arr.get(i));
            }else
                continue;

        }
        if (k <= arr1.size()){
            return quickSelectFirst(arr1,k);
        }else if(k>arr.size()-arr2.size()){
            return quickSelectFirst(arr2,k-(arr.size()-arr2.size()));
        }else
            return pivot;

    }

    static void partialSelectionSort(int[] array,int k){
        for (int i = 0;i<k;i++){
            int minIndex = i;
            int minValue = array[i];

            for(int j = i+1;j<array.length;j++){
                count++;
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
            count +=2;
            }
            array[j + 1] = key;
            count++;
        }

    }


    static void merge(int[] array, int l, int x, int r) { //Method of merging for Merge Sort

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
                count++;

                if (tempArray1[i] <= tempArray2[j]) {
                    array[k] = tempArray1[i];
                    i++;

                } else {

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


    static int partition1(int []a,int start,int end)

    {

        int pivot=a[start],p1=start+1,i,temp;

        for(i=start+1;i<=end;i++)
        {

            if(a[i]<pivot)
            {
                if(i!=p1)
                {
                    count++;
                    temp=a[p1];
                    a[p1]=a[i];
                    a[i]=temp;

                }
                p1++;
            }
        }

        a[start]=a[p1-1];
        a[p1-1]=pivot;

        return p1-1;
    }

    static void quickSort(int []a,int start,int end)
    {

        int p1;
        if(start<end)
        {
            p1=partition1(a,start,end);
            quickSort(a,start,p1-1);
            quickSort(a,p1+1,end);
        }

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
       count++;
    }


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
        count++;
    }


}
