package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.model.home.TermSelectInfoBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;
import cn.finalteam.toolsfinal.io.FileUtils;

import static cn.finalteam.toolsfinal.io.Charsets.UTF_8;


/**
 * Created by MummyDing on 2017/5/25.
 * 每个学期时间
 */

public class TermInfoParse {

    private final static String TAG = "TermInfoParse";
    private final static String TERM_SELECT_INFO_FILE = "termSelectFile.json";
    private final static String TERM_TAG_CSS = "option";
    private final static String TERM_ATTR_CSS = "value";
    private String mRawString;
    private List<String> mRawTermDateList;
    private List<String> mRawTermDescList;
    private TermSelectInfoBean mTermSelectInfo = new TermSelectInfoBean();

    public TermInfoParse() {
    }

    public TermInfoParse(String rawString) {
        this.mRawString = rawString;
        parseData();
        saveData();
    }

    public TermSelectInfoBean getTermSelectInfo() {
        if (mTermSelectInfo.getTermList().isEmpty()) {
            try {
                File file = new File(InitApp.AppContext.getFilesDir() + TERM_SELECT_INFO_FILE);
                if (file.exists() == false) {
                    file.createNewFile();
                }
                String rawData = FileUtils.readFileToString(file);
                if (TextUtil.isNull(rawData) == false) {
                    Gson gson = new Gson();
                    mTermSelectInfo = gson.fromJson(rawData, TermSelectInfoBean.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mTermSelectInfo;
    }

    public void updateCurrentTermIndex(int index) {
        if (mTermSelectInfo != null) {
            mTermSelectInfo.setCurrentSelectIndex(index);
            saveData();
        }
    }

    private void saveData() {
        if (mTermSelectInfo.getTermList().isEmpty()) {
            return;
        }
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(mTermSelectInfo);
        try {
            FileUtils.writeStringToFile(new File(InitApp.AppContext.getFilesDir() + TERM_SELECT_INFO_FILE), jsonData, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseData() {
        try {
            HtmlUtil htmlUtil = new HtmlUtil(mRawString);
            mRawTermDateList = htmlUtil.parseStringByAttr(TERM_TAG_CSS, TERM_ATTR_CSS);
            mRawTermDescList = htmlUtil.parseString(TERM_TAG_CSS);
            buildModel();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

    private void buildModel() {
        getTermSelectInfo();
        mTermSelectInfo.removeTermInfo();
        if (mTermSelectInfo.getCurrentSelectIndex() == -1) {
            mTermSelectInfo.setCurrentSelectIndex(mRawTermDateList.size() - 1);
        }
        for (int i = mRawTermDateList.size() - 1; i >= 0 ; i--) {
            String[] tempList = mRawTermDescList.get(i).split("第");
            TermSelectInfoBean.TermInfo termInfo = new TermSelectInfoBean.TermInfo(mRawTermDateList.get(i), tempList[0] + "学年", "第" + tempList[1]);
            mTermSelectInfo.addTermInfo(termInfo);
        }
    }

}
