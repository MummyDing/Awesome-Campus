package cn.edu.jxnu.awesome_campus.support.weather;

import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by KevinWu on 16-5-1.
 */
public class WeatherConfig {
    public static int getWeatherPicNum(String weastherInfo) {
        String isSeperate[] = weastherInfo.split("转");
        String endweather = null;
        if (isSeperate.length > 1) {
            String tempStr[] = isSeperate[1].split("到");
            if(tempStr.length>1)
                endweather = tempStr[tempStr.length - 1];
            else{
                String tempStr2[] = isSeperate[0].split("-");
                endweather = tempStr2[tempStr2.length - 1];
            }
        } else {
            String tempStr[] = isSeperate[0].split("到");
            if(tempStr.length>1)
            endweather = tempStr[tempStr.length - 1];
            else{
                String tempStr2[] = isSeperate[0].split("-");
                    endweather = tempStr2[tempStr2.length - 1];
            }
        }
        int returnNum = 0;
        switch (endweather) {
            case "晴":
                returnNum = 1;
                break;
            case "多云":
                returnNum = 2;
                break;
            case "阴":
                returnNum = 3;
                break;
            case "阵雨":
                returnNum = 4;
                break;
            case "雷阵雨":
                returnNum = 5;
                break;
            case "雷阵雨伴有冰雹":
                returnNum = 6;
                break;
            case "雨夹雪":
                returnNum = 7;
                break;
            case "小雨":
                returnNum = 8;
                break;
            case "中雨":
                returnNum = 9;
                break;
            case "大雨":
                returnNum = 10;
                break;
            case "暴雨":
                returnNum = 11;
                break;
            case "大暴雨":
                returnNum = 12;
                break;
            case "特大暴雨":
                returnNum = 13;
                break;
            case "阵雪":
                returnNum = 14;
                break;
            case "小雪":
                returnNum = 15;
                break;
            case "中雪":
                returnNum = 16;
                break;
            case "大雪":
                returnNum = 17;
                break;
            case "暴雪":
                returnNum = 18;
                break;
            case "雾":
                returnNum = 19;
                break;
            case "霾":
                returnNum = 20;
                break;
            case "冻雨":
                returnNum = 21;
                break;
            case "沙尘暴":
            case "强沙尘暴":
                returnNum = 22;
                break;
            case "浮尘":
                returnNum = 23;
                break;
            case "扬沙":
                returnNum = 24;
                break;
            default:
                returnNum = 0;
                break;
        }
        return returnNum;
    }

    public static int WeatherPic[] = {
            R.drawable.unknown,
            R.drawable.weather_qing,
            R.drawable.weather_duoyun,
            R.drawable.weather_yin,
            R.drawable.weather_zhenyu,
            R.drawable.weather_leizhenyu,
            R.drawable.weather_leizhenyubanyoubingbao,
            R.drawable.weather_yujiaxue,
            R.drawable.weather_xiaoyu,
            R.drawable.weather_zhongyu,
            R.drawable.weather_dayu,
            R.drawable.weather_baoyu,
            R.drawable.weather_dabaoyu,
            R.drawable.weather_tedabaoyu,
            R.drawable.weather_zhenxue,
            R.drawable.weather_xiaoxue,
            R.drawable.weather_zhongxue,
            R.drawable.weather_daxue,
            R.drawable.weather_baoxue,
            R.drawable.weather_wu,
            R.drawable.weather_mai,
            R.drawable.weather_dongyu,
            R.drawable.weather_shachenbao,
            R.drawable.weather_fuchen,
            R.drawable.weather_yangsha
    };
}
