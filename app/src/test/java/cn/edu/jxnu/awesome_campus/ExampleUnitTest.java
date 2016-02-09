package cn.edu.jxnu.awesome_campus;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void testPrase(){
//        String str=importStr();
//        System.out.println(str);
//        CourseScoreParse c=new CourseScoreParse(str);
//        List testlist=c.getResultList();
//        for(int i=0;i<testlist.size();i++){
//            System.out.println(testlist.get(i).toString());
//        }
//    }
//
//    private String importStr(){
//        String str="";
//        File myFile=new File("./htmlT.txt");
//        try {
//            FileReader myFileReader=new FileReader(myFile);
//            BufferedReader myBufferedReader=new BufferedReader(myFileReader);
//            String temp;
//            while((temp=myBufferedReader.readLine())!=null){
//                str=str+temp;
//            }
//            myBufferedReader.close();
//            myFileReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
}