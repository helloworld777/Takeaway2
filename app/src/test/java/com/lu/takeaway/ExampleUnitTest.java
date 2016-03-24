package com.lu.takeaway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        List<String> list=new ArrayList<>();
        list.add("a");
        list.add("a1");
        list.add("a2");
        list.add("a3");
        for(int i=0;i<list.size();i++ ){
            if(list.get(i).equals("a")){
                list.remove(i);
            }
        }
//        KeysUtil keysUtil=new KeysUtil();
        assertEquals(4, 2 + 2);
    }
}