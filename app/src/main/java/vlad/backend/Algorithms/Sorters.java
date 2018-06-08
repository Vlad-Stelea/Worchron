package vlad.backend.Algorithms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Sorters {

    /**
     * Produces a sorted list using the quicksort algorithm
     * @param toSort the list to sort
     * @return A list of sorted elements
     */
    public static <T extends Comparable<? super T>> List<T> quickSort(List<T> toSort){
        quickSortArray(toSort, 0, toSort.size()-1);
        //Add everything back into the original List
        return toSort;
    }

    /**
     * Sorts an array using the quicksort algorithm
     * @param toSort the array to sort
     * @param left where to start sorting from
     * @param right where to stop sorting
     */
    private static <T extends Comparable<? super T>> void quickSortArray(List<T> toSort, int left, int right){
        if(left >=right){
            return;
        }
        T pivot = choosePivot(toSort, left, right);
        int index = partition(toSort, left, right,pivot);
        if(index> right)
            return;
        quickSortArray(toSort, left, index -1);
        quickSortArray(toSort,index,right);
        /*//base case
        if(left >= right)
            return;
        if(right - left == 1
                && toSort[left].compareTo(toSort[right]) > 0){
            swapArrayElement(toSort,left,right);
            return;
        }

        //get pivot and swap it with the right element
        //This gets it out of the way
        int pivotIndex = choosePivot(toSort, left, right);
        Comparable pivot = toSort[pivotIndex];
        swapArrayElement(toSort, pivotIndex, right);
        pivotIndex = right;
        //Set up lefts and rights
        int itemFromLeft = findItemFromLeft(toSort,left,right-1,pivot);
        int itemFromRight = findItemFromRight(toSort,left,right-1,pivot);
        //
        while(itemFromLeft < itemFromRight){
            swapArrayElement(toSort, itemFromLeft, itemFromRight);
            itemFromLeft++;
            itemFromRight--;
            itemFromLeft = findItemFromLeft(toSort,itemFromLeft,itemFromRight,pivot);
            itemFromRight = findItemFromRight(toSort,itemFromLeft,itemFromRight,pivot);
        }
        swapArrayElement(toSort,itemFromLeft,pivotIndex);
        //quicksort left
        quickSortArray(toSort, left,itemFromLeft);
        //quicksort right
        quickSortArray(toSort,itemFromLeft + 1, right);*/
    }


    private static <T extends Comparable<? super T>> int partition(List<T> A, int lo, int hi, T pivot){
        while(lo <=hi){
            while(A.get(lo).compareTo(pivot) < 0){
                lo++;
            }

            while(A.get(hi).compareTo(pivot) > 0){
                hi--;
            }
            if(lo <= hi){
                swapElement(A, hi, lo);
                lo++;
                hi--;
            }
        }
        return lo;
    }
    private static void swapElement(List array, int index1, int index2){
        Object temp = array.get(index1);
        array.set(index1, array.get(index2));
        array.set(index2,temp);
    }

    /**
     * Turns a list of comparables into an array
     * makes it easier to read
     * @param list the list to make into an array
     * @return an array of comparable objects
     */
    private static Comparable[] makeArray(List<Comparable> list){
        Comparable [] toReturn = new Comparable[list.size()];
        int index = 0;
        for(Comparable toAdd : list){
            toReturn[index] = toAdd;
            index++;
        }
        return toReturn;
    }

    /**
     * Chooses a pivot for the quicksort algorithm
     * @param array the array that will be quicksorted
     * @return the index of the array that will be the pivot
     */
    private static <T extends Comparable<? super T>> T choosePivot(List<T> array, int left, int right){
        return array.get(right-1);
    }

    /**
     * Inserts an element into a list at the correct position based on ascending order
     * @implNote uses binary insertion method for fast runtime
     * @implSpec the passed in list must be already sorted
     * @param list the list to add the new object into
     * @param toInsert the element to add into the list at the correct position
     */
    public static <T extends Comparable<? super T>> void binaryInsert(List<T> list, T toInsert){
        //constant that holds where the new item should be inserted
        final int position = arrayBinaryInsert(list, 0, list.size(), toInsert);
        list.add(position, toInsert);
    }

    /**
     * finds the position that a comparable object belongs in
     * @param array the original array
     * @param lo the smallest index in the search radius
     * @param hi the largest index in the search radius
     * @param pivot what is going to be added into the list
     * @return the index that the pivot should be inserted into
     */
    private static <T extends Comparable<? super T>> int arrayBinaryInsert(List<T> array, int lo, int hi, T pivot){
        //base case
        if(hi - lo <= 1)
            return lo+1;
        int midPoint = ((lo + hi) / 2);
        int compareResult = pivot.compareTo(array.get(midPoint));
        if(compareResult == 0){
            return midPoint;
        }else if(compareResult > 0){
            return arrayBinaryInsert(array, midPoint, hi, pivot);
        }else{
            return arrayBinaryInsert(array, lo, midPoint,pivot);
        }
    }
}
