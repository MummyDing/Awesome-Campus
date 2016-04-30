package cn.edu.jxnu.awesome_campus.model.life;

/**
 * Created by MummyDing on 16-4-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoBean  {
    private String reason;
    private Result result;
    private String error_code;
    static class Result{
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }
    static class Data{
        public WeatherInfoModel getWeather() {
            return weather;
        }

        public void setWeather(WeatherInfoModel weather) {
            this.weather = weather;
        }

        private WeatherInfoModel weather;
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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
