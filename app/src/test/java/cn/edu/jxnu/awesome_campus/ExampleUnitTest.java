package cn.edu.jxnu.awesome_campus;

import org.json.JSONArray;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import cn.edu.jxnu.awesome_campus.support.htmlparse.leisure.JianshuListParse;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testPrase(){
        String str=importStr();
        System.out.println(str);
        JianshuListParse myparse=new JianshuListParse(str);
        List l=myparse.getEndList();
        for(int i=0;i<l.size();i++){
            System.out.println(l.get(i).toString());
        }

    }

    private String importStr(){
        String str="";
        File myFile=new File("./jianshu.txt");
        try {
            FileReader myFileReader=new FileReader(myFile);
            BufferedReader myBufferedReader=new BufferedReader(myFileReader);
            String temp;
            while((temp=myBufferedReader.readLine())!=null){
                str=str+temp;
            }
            myBufferedReader.close();
            myFileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}