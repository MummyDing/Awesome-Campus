package cn.edu.jxnu.awesome_campus.presenter;

import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.view.IView;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface IPresenter<M extends IModel<M>,V extends IView<V>> {

    void setModel(M m);
    M getModel();
    void setView(V v);
    V getView();
    void initlization();

}
