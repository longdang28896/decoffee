package vn.touchspace.example.ui.main.info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.touchspace.example.R;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.touchspace.example.data.network.model.response.User;
import vn.touchspace.example.ui.base.BaseFragment;
import vn.touchspace.example.ui.main.info.updateinfo.UpdateInfoActivity;
import vn.touchspace.example.ui.main.info.updateinfo.UpdatePasswordActivity;
import vn.touchspace.example.utils.AppUtils;

public class InfoFragment extends BaseFragment implements InfoMvpView {

    @Inject
    InfoMvpPresenter<InfoMvpView> mPresenter;
    @BindView(R.id.tv_avatar)
    TextView tvAvatar;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_update_info)
    TextView tvUpdateInfo;
    @BindView(R.id.tv_update_pass)
    TextView tvUpdatePass;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private User user;

    public static InfoFragment newInstance() {

        Bundle args = new Bundle();

        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info;
    }

    @Override
    protected void init(Bundle saveInstanceState, View rootView) {
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        mPresenter.getInfo();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick({R.id.tv_update_info, R.id.tv_update_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_info:
                Intent intent = new Intent(getActivity(), UpdateInfoActivity.class);
                startActivity(intent);
                AppUtils.isDoubleClick(tvUpdateInfo);
                break;
            case R.id.tv_update_pass:
                intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                startActivity(intent);
                AppUtils.isDoubleClick(tvUpdatePass);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getInfo();
    }

    @Override
    public void getInfoSuccess(User user) {
        this.user = user;
        tvName.setText(user.getFullName());
        AppUtils.setDefaultAvatar(tvAvatar, user.getFullName());
        tvRole.setText(AppUtils.getRole(user.getRole()));
        tvBirthday.setText(user.getBirthday());
        tvTelephone.setText(user.getTelephoneNumber());
    }

}
