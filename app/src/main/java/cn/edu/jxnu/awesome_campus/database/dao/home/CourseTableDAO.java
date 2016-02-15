package cn.edu.jxnu.awesome_campus.database.dao.home;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.education.CourseTableParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableDAO implements DAO<CourseTableModel> {
    public static final String TAG="CourseTableDAO";
    @Override
    public boolean cacheAll(List<CourseTableModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = "_ga=GA1.3.609810117.1451115712; ASP.NET_SessionId=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE) +
                ";JwOAUserSettingNew=" +
                spu.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE);
        NetManageUtil.post(Urlconfig.CourseTable_URL)
                .addParams("__EVENTTARGET","")
                .addParams("__EVENTARGUMENT","")
                .addParams("__VIEWSTATE", "/wEPDwUJNzIzMTk0NzYzD2QWAgIBD2QWCgIBDw8WAh4EVGV4dAUgMjAxNuW5tDLmnIgxNeaXpSDmmJ/mnJ/kuIAmbmJzcDtkZAIFDw8WAh8ABRjlvZPliY3kvY3nva7vvJror77nqIvooahkZAIHDw8WAh8ABS0gICDmrKLov47mgqjvvIwoMTMwODA5NTA3OCxTdHVkZW50KSDlkLTlkK/kuJxkZAIKD2QWBAIBDw8WAh4ISW1hZ2VVcmwFQy4uL015Q29udHJvbC9BbGxfUGhvdG9TaG93LmFzcHg/VXNlck51bT0xMzA4MDk1MDc4JlVzZXJUeXBlPVN0dWRlbnRkZAIDDxYCHwAFiCI8ZGl2IGlkPSdtZW51UGFyZW50XzAnIGNsYXNzPSdtZW51UGFyZW50JyBvbmNsaWNrPSdtZW51R3JvdXBTd2l0Y2goMCk7Jz7miJHnmoTkv6Hmga88L2Rpdj48ZGl2IGlkPSdtZW51R3JvdXAwJyBjbGFzcz0nbWVudUdyb3VwJz48RGl2IGNsYXNzPSdtZW51SXRlbU9uJyB0aXRsZT0n6K++56iL6KGoJz48YSBocmVmPSJkZWZhdWx0LmFzcHg/JmNvZGU9MTExJiZ1Y3RsPU15Q29udHJvbFx4Znpfa2NiLmFzY3gmTXlBY3Rpb249UGVyc29uYWwiIHRhcmdldD0ncGFyZW50Jz7or77nqIvooag8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfln7rmnKzkv6Hmga8nPjxhIGhyZWY9Ii4uXE15Q29udHJvbFxTdHVkZW50X0luZm9yQ2hlY2suYXNweCIgdGFyZ2V0PSdfYmxhbmsnPuWfuuacrOS/oeaBrzwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+S/ruaUueWvhueggSc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTExMCYmdWN0bD1NeUNvbnRyb2xccGVyc29uYWxfY2hhbmdlcHdkLmFzY3giIHRhcmdldD0ncGFyZW50Jz7kv67mlLnlr4bnoIE8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSflrabnsY3pooToraYnPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygneGZ6X2J5c2guYXNjeCZBY3Rpb249UGVyc29uYWwnKTsiIHRhcmdldD0nJz7lrabnsY3pooToraY8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfmlrDnlJ/lr7zluIgnPjxhIGhyZWY9ImRlZmF1bHQuYXNweD8mY29kZT0yMTQmJnVjdGw9TXlDb250cm9sXHN0dWRlbnRfbXl0ZWFjaGVyLmFzY3giIHRhcmdldD0ncGFyZW50Jz7mlrDnlJ/lr7zluIg8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfor77nqIvmiJDnu6knPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygneGZ6X2NqLmFzY3gmQWN0aW9uPVBlcnNvbmFsJyk7IiB0YXJnZXQ9Jyc+6K++56iL5oiQ57upPC9hPjwvZGl2PjxEaXYgY2xhc3M9J21lbnVJdGVtJyB0aXRsZT0n5omL5py65Y+356CBJz48YSBocmVmPSIuLlxNeUNvbnRyb2xcUGhvbmUuYXNweCIgdGFyZ2V0PSdfYmxhbmsnPuaJi+acuuWPt+eggTwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+WutumVv+eZu+W9lSc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTIwMyYmdWN0bD1NeUNvbnRyb2xcSnpfc3R1ZGVudHNldHRpbmcuYXNjeCIgdGFyZ2V0PSdwYXJlbnQnPuWutumVv+eZu+W9lTwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+WPjOS4k+S4muWPjOWtpuS9jeivvueoi+WuieaOkuihqCc+PGEgaHJlZj0iLi5cTXlDb250cm9sXERlenlfa2IuYXNweCIgdGFyZ2V0PSdfYmxhbmsnPuWPjOS4k+S4muWPjOWtpuS9jeivvueoi+WuieaOkuihqDwvYT48L2Rpdj48L2Rpdj48ZGl2IGlkPSdtZW51UGFyZW50XzEnIGNsYXNzPSdtZW51UGFyZW50JyBvbmNsaWNrPSdtZW51R3JvdXBTd2l0Y2goMSk7Jz7lhazlhbHmnI3liqE8L2Rpdj48ZGl2IGlkPSdtZW51R3JvdXAxJyBjbGFzcz0nbWVudUdyb3VwJz48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+WfueWFu+aWueahiCc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTEwNCYmdWN0bD1NeUNvbnRyb2xcYWxsX2p4amguYXNjeCIgdGFyZ2V0PSdwYXJlbnQnPuWfueWFu+aWueahiDwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+ivvueoi+S/oeaBryc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTExNiYmdWN0bD1NeUNvbnRyb2xcYWxsX2NvdXJzZXNlYXJjaC5hc2N4IiB0YXJnZXQ9J3BhcmVudCc+6K++56iL5L+h5oGvPC9hPjwvZGl2PjxEaXYgY2xhc3M9J21lbnVJdGVtJyB0aXRsZT0n5byA6K++5a6J5o6SJz48YSBocmVmPSIuLlxNeUNvbnRyb2xcUHVibGljX0trYXAuYXNweCIgdGFyZ2V0PSdfYmxhbmsnPuW8gOivvuWuieaOkjwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+WtpueUn+S/oeaBryc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTExOSYmdWN0bD1NeUNvbnRyb2xcYWxsX3NlYXJjaHN0dWRlbnQuYXNjeCIgdGFyZ2V0PSdwYXJlbnQnPuWtpueUn+S/oeaBrzwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+aVmeW3peS/oeaBryc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTEyMCYmdWN0bD1NeUNvbnRyb2xcYWxsX3RlYWNoZXIuYXNjeCIgdGFyZ2V0PSdwYXJlbnQnPuaVmeW3peS/oeaBrzwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+efreS/oeW5s+WPsCc+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTEyMiYmdWN0bD1NeUNvbnRyb2xcbWFpbF9saXN0LmFzY3giIHRhcmdldD0ncGFyZW50Jz7nn63kv6HlubPlj7A8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfmlZnlrqTmlZnlrablronmjpInPjxhIGhyZWY9Ii4uXE15Q29udHJvbFxwdWJsaWNfY2xhc3Nyb29tLmFzcHgiIHRhcmdldD0nX2JsYW5rJz7mlZnlrqTmlZnlrablronmjpI8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSflj4zlrabkvY3or77nqIvmiJDnu6knPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygnZGV6eV9jai5hc2N4JkFjdGlvbj1QZXJzb25hbCcpOyIgdGFyZ2V0PScnPuWPjOWtpuS9jeivvueoi+aIkOe7qTwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+avleS4mueUn+WbvuWDj+mHh+mbhuS/oeaBr+agoeWvuSc+PGEgaHJlZj0iLi5cTXlDb250cm9sXFRYQ0pfSW5mb3JDaGVjay5hc3B4IiB0YXJnZXQ9J19ibGFuayc+5q+V5Lia55Sf5Zu+5YOP6YeH6ZuG5L+h5oGv5qCh5a+5PC9hPjwvZGl2PjxEaXYgY2xhc3M9J21lbnVJdGVtJyB0aXRsZT0n5pyf5pyr5oiQ57up5p+l6K+iJz48YSBocmVmPSJqYXZhc2NyaXB0Ok9wZW5XaW5kb3coJ3hmel9UZXN0X2NqLmFzY3gnKTsiIHRhcmdldD0nJz7mnJ/mnKvmiJDnu6nmn6Xor6I8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfmnJ/mnKvmiJDnu6nmn6XliIbnlLPor7cnPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygnQ2ZzcV9TdHVkZW50LmFzY3gnKTsiIHRhcmdldD0nJz7mnJ/mnKvmiJDnu6nmn6XliIbnlLPor7c8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfooaXnvJPogIPlronmjpInPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygneGZ6X1Rlc3RfQkhLLmFzY3gnKTsiIHRhcmdldD0nJz7ooaXnvJPogIPlronmjpI8L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSflrabkuaDpl67nrZQnPjxhIGhyZWY9ImRlZmF1bHQuYXNweD8mY29kZT0xNTkmJnVjdGw9TXlDb250cm9sXEFsbF9TdHVkeV9MaXN0LmFzY3giIHRhcmdldD0ncGFyZW50Jz7lrabkuaDpl67nrZQ8L2E+PC9kaXY+PC9kaXY+PGRpdiBpZD0nbWVudVBhcmVudF8yJyBjbGFzcz0nbWVudVBhcmVudCcgb25jbGljaz0nbWVudUdyb3VwU3dpdGNoKDIpOyc+5pWZ5a2m5L+h5oGvPC9kaXY+PGRpdiBpZD0nbWVudUdyb3VwMicgY2xhc3M9J21lbnVHcm91cCc+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPSfnvZHkuIror4TmlZknPjxhIGhyZWY9ImphdmFzY3JpcHQ6T3BlbldpbmRvdygncGpfc3R1ZGVudF9pbmRleC5hc2N4Jyk7IiB0YXJnZXQ9Jyc+572R5LiK6K+E5pWZPC9hPjwvZGl2PjxEaXYgY2xhc3M9J21lbnVJdGVtJyB0aXRsZT0n5pWZ5Yqh5oSP6KeB566xJz48YSBocmVmPSIuLi9EZWZhdWx0LmFzcHg/QWN0aW9uPUFkdmlzZSIgdGFyZ2V0PSdfYmxhbmsnPuaVmeWKoeaEj+ingeeusTwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+acn+acq+iAg+ivleWuieaOkic+PGEgaHJlZj0iZGVmYXVsdC5hc3B4PyZjb2RlPTEyOSYmdWN0bD1NeUNvbnRyb2xceGZ6X3Rlc3Rfc2NoZWR1bGUuYXNjeCIgdGFyZ2V0PSdwYXJlbnQnPuacn+acq+iAg+ivleWuieaOkjwvYT48L2Rpdj48RGl2IGNsYXNzPSdtZW51SXRlbScgdGl0bGU9J+i+heS/ruWPjOS4k+S4muWPjOWtpuS9jeaKpeWQjSc+PGEgaHJlZj0iamF2YXNjcmlwdDpPcGVuV2luZG93KCdEZXp5X2JtLmFzY3gnKTsiIHRhcmdldD0nJz7ovoXkv67lj4zkuJPkuJrlj4zlrabkvY3miqXlkI08L2E+PC9kaXY+PERpdiBjbGFzcz0nbWVudUl0ZW0nIHRpdGxlPScyMDE057qn5pys56eR5a2m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")
                .addParams("__EVENTVALIDATION","/wEWCwLHm5KICQKKhuW9AQL9g+OSAgLeg4+HCQL9g/eyBwLItunkDwLvttGQDQLItv0EAu+25bAOAoaZ/bIBAubhijOiLcziWMHTcBNdUwLaQZXvx+1j9jJQC3d4++H0Y7FdIQ==")
               .addParams("_ctl1:ddlSterm", spu.getStringSP(EducationStaticKey.SP_FILE_NAME,EducationStaticKey.NOW_TERM))
                .addParams("_ctl1:btnSearch","确定")
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                Log.d("result len: ",result.length()+" ");
                Log.d("result ",result.substring(10,100));
                Log.d("result ",result.substring(100,200));
                Log.d("result ",result.substring(200,300));
                Log.d("result ",result.substring(100,200));
               /* CourseTableParse myParse = new CourseTableParse(result);
                final List list = myParse.getEndList();
                System.out.println("列表大小：" + list.size());
                for (int i = 0; i < list.size(); i++)
                    System.out.println("列表数据：" + list.get(i).toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            cacheAll(list);
                            EventBus.getDefault().post(new EventModel<CourseTableModel>(EVENT.COURSE_TABLE_REFRESH_SUCCESS, list));
                        } else {
                            EventBus.getDefault().post(new EventModel<CourseTableModel>(EVENT.COURSE_TABLE_REFRESH_FAILURE));
                        }
                    }
                });*/
            }

            @Override
            public void onFailure(String error) {
                Log.d("课程表获取失败", error);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CourseScoreModel>(EVENT.COURSE_TABLE_REFRESH_FAILURE));

                    }
                });
            }
        });
    }
}
