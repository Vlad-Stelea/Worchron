package vlad.worchron;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vlad.backend.Algorithms.Sorters;

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
    @Before
    public void setUp(){
        testList = new ArrayList<>(Arrays.asList(unsorted));
        sortedList = new ArrayList<>(Arrays.asList(sorted));
        stringTestList = new ArrayList<>(Arrays.asList(unsortedString));
        stringSortedList = new ArrayList<>(Arrays.asList(sortedString));
    }

    @Test
    public void testQuicksort(){
        //assertEquals(sortedList, Sorters.quickSort(testList));
        assertEquals(stringSortedList, Sorters.quickSort(stringTestList));
    }
}
