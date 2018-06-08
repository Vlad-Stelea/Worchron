package vlad.worchron;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vlad.backend.Algorithms.Sorters;
import vlad.backend.Exercises.SelectableExercise;

import static org.junit.Assert.*;

public class SortersTests {
    private List<Comparable> testList;
    private List<Comparable> sortedList;
    private List<Comparable> stringTestList;
    private List<Comparable> stringSortedList;
    private Integer[] unsorted = {5, 3, 2, 4, 7, 0, -1, -1};
    private Integer[] sorted = {-1, -1, 0,2,3,4,5,7};

    private String[] unsortedString = {"a", "b"};
    private String[] sortedString = {"a", "b"};

    private List<SelectableExercise> sortedExercises;
    private List<SelectableExercise> unSortedExercises;

    @Before
    public void setUp(){
        testList = new ArrayList<>(Arrays.asList(unsorted));
        sortedList = new ArrayList<>(Arrays.asList(sorted));
        stringTestList = new ArrayList<>(Arrays.asList(unsortedString));
        stringSortedList = new ArrayList<>(Arrays.asList(sortedString));
        unSortedExercises = new ArrayList<>();
        sortedExercises = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            unSortedExercises.add(new SelectableExercise("" + (int) (Math.random() *100 + 1)));
        }
    }

    @Test
    public void testQuicksort(){
        assertEquals(sortedList, Sorters.quickSort(testList));
        SelectableExercise t = new SelectableExercise("TEST1");
        SelectableExercise t2 = new SelectableExercise("Test2");
        t.compareTo(t2);
        assertEquals(stringSortedList, Sorters.quickSort(stringTestList));
    }


    @Test
    public void testBinaryInsert(){
        sortedExercises = Sorters.quickSort(unSortedExercises);
        Sorters.binaryInsert(sortedExercises, new SelectableExercise("" + (int) (Math.random() * 100 + 1)));
        assertTrue(testInOrder(sortedExercises));
    }

    private boolean testInOrder(List<? extends  Comparable> toTest){
        if(toTest.size() < 2)
            return true;
        for(int i = 0; i < toTest.size() -1; i++){
            if(toTest.get(i).compareTo(toTest.get(i+1)) > 0)
                return false;
        }
        return true;
    }
}
