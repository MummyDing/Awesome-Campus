package cn.edu.jxnu.awesome_campus.model.life;

/**
 * Created by MummyDing on 16-4-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoBean  {
    private String reason;
    private Result result;
    private int error_code;
    public static class Result{
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }
    public static class Data{

        private WeatherInfoModel [] weather;

        public WeatherInfoModel[] getWeather() {
            return weather;
        }

        public void setWeather(WeatherInfoModel[] weather) {
            this.weather = weather;
        }
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
