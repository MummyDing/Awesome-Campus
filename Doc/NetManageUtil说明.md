## NetMangeUtil 网络请求工具的说明

1. 基本功能
```java
NetManageUtil.get("http://baidu.com/login")   // 这里填写url   如果是post 请求 直接将get 改成 post()就好
                .addHeader("Upgrade-Insecure-Requests","1")  // 添加头部
                .addParams("username","Mummy")  // get 的参数 
                .addTag(TAG)  // 这个TAG是用来表示请求的 给个字符串就好 主要是为了可以取消请求
                .enqueue(new StringCallback() {  
                // 入队,这样请求就开始了 还有一个callback是 InputStreamCallback 你要返回哪个就用哪个
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        headers.get("Cookie");// 这里可以将返回的头部参数取出来,比如cookie
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });
        
NetManageUtil.cancelByTag(TAG); // 这个是用来取消tag 为 TAG的所有请求的,这个主要是为了在页面跳转的时候将之前没有完成的请求取消掉
        
```        

2. 整合gson,自动解析json数据

```java
NetManageUtil.get("https://api.douban.com/v2/book/search?q=python&count=2")
                .enqueue(new JsonEntityCallback<Book>() { //和上面的区别就是 Callback  泛型,填上实体类即可
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(Book entity, Headers headers) {
                        Log.e("Good","----"+entity.getBooks()[0].getSubtitle()+"---\n"+entity.getCount());
                    }
                });
```                   
