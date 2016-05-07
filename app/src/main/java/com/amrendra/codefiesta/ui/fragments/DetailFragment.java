package com.amrendra.codefiesta.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.progressbar.CustomProgressBar;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.CustomDate;
import com.amrendra.codefiesta.utils.DateUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.bumptech.glide.Glide;

import java.util.TimeZone;

import butterknife.Bind;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends BaseFragment {

    Contest contest;

    @Bind(R.id.detail_fragment_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.contest_title_tv)
    TextView contestTitleTv;
    @Bind(R.id.contest_website_tv)
    TextView contestWebsiteTv;
    @Bind(R.id.resource_logo)
    ImageView resourceImageView;
    @Bind(R.id.status_tv)
    TextView statusTv;

    @Bind(R.id.contest_start_time_tv)
    TextView contestStartTime;
    @Bind(R.id.contest_start_time_ampm)
    TextView contestStartAmPm;
    @Bind(R.id.contest_start_date_tv)
    TextView contestStartDate;

    @Bind(R.id.contest_end_time_tv)
    TextView contestEndTime;
    @Bind(R.id.contest_end_time_ampm)
    TextView contestEndAmPm;
    @Bind(R.id.contest_end_date_tv)
    TextView contestEndDate;

    @Bind(R.id.contest_timezone_tv)
    TextView timeZoneTv;

    @Bind(R.id.calendar_image)
    FloatingActionButton calendarImageView;
    @Bind(R.id.notification_image)
    FloatingActionButton notificationImageView;
    @Bind(R.id.share_image)
    FloatingActionButton shareImageView;
    @Bind(R.id.link_website)
    FloatingActionButton websiteLink;

    @Bind(R.id.progress_bar_days)
    CustomProgressBar daysProgressBar;
    @Bind(R.id.progress_bar_hours)
    CustomProgressBar hoursProgressBar;
    @Bind(R.id.progress_bar_mins)
    CustomProgressBar minsProgressBar;
    @Bind(R.id.progress_bar_sec)
    CustomProgressBar secProgressBar;

    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            contest = bundle.getParcelable(AppUtils.CONTEST_ID_KEY);
        } else {
            Debug.e("Should not happen. DetailFragmet needs to have contestId", false);
        }
        Debug.e("contest " + contest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDetails();
    }

    private void updateDetails() {
        if (contest != null) {
            contestWebsiteTv.setText(contest.getWebsite().getName());

            contestTitleTv.setText(contest.getEvent());
            TimeZone tz = TimeZone.getDefault();
            timeZoneTv.setText(tz.getID() + " " + tz.getDisplayName(false, TimeZone.SHORT));


            final long starTime = DateUtils.getEpochTime(contest.getStart());
            final long endTime = DateUtils.getEpochTime(contest.getEnd());
            final CustomDate startDate = new CustomDate(DateUtils.epochToDateTimeLocalShow(starTime));
            final CustomDate endDate = new CustomDate(DateUtils.epochToDateTimeLocalShow(endTime));

            final String starts = "Starts : " + startDate.getTime() + " " + startDate.getAmPm() + " " +
                    "" + startDate.getDateMonthYear();
            final String ends = "Ends : " + endDate.getTime() + " " + endDate.getAmPm() + " " +
                    "" + endDate.getDateMonthYear();

            contestStartTime.setText(startDate.getTime());
            contestStartAmPm.setText(startDate.getAmPm());
            contestStartDate.setText(startDate.getDateMonthYear());

            contestEndTime.setText(endDate.getTime());
            contestEndAmPm.setText(endDate.getAmPm());
            contestEndDate.setText(endDate.getDateMonthYear());

            statusTv.setText(DateUtils.getContestStatusString(starTime, endTime));

            final int resourceId = contest.getWebsite().getId();
            String resourceName = AppUtils.getResourceName(getActivity(), resourceId);
            final String shortResourceName = AppUtils.getGoodResourceName(resourceName);
            contestWebsiteTv.setText(shortResourceName);
            Glide.with(getActivity())
                    .load(AppUtils.getImageForResource(resourceName))
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(resourceImageView);


            calendarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int contestStatus = DateUtils.getContestStatus(starTime, endTime);
                    if (contestStatus == AppUtils.STATUS_CONTEST_FUTURE) {

                    } else if (contestStatus == AppUtils.STATUS_CONTEST_LIVE) {
                        String text = String.format(getString(R.string.contest_started),
                                contest,
                                shortResourceName);
                        onError(text);
                    } else {
                        String text = String.format(getString(R.string.contest_ended),
                                contest,
                                shortResourceName);
                        onError(text);
                    }
                }
            });

            notificationImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int contestStatus = DateUtils.getContestStatus(starTime, endTime);
                    if (contestStatus == AppUtils.STATUS_CONTEST_FUTURE) {

                    } else if (contestStatus == AppUtils.STATUS_CONTEST_LIVE) {
                        String text = String.format(getString(R.string.contest_started),
                                contest.getEvent(),
                                shortResourceName);
                        onError(text);
                    } else {
                        String text = String.format(getString(R.string.contest_ended),
                                contest.getEvent(),
                                shortResourceName);
                        onError(text);
                    }
                }
            });

            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Checkout this contest!!\n");
                    sb.append(contest).append("\n");
                    sb.append("@ ").append(shortResourceName).append("\n");
                    sb.append(starts).append("\n");
                    sb.append(ends).append("\n");
                    sb.append("#").append(getString(R.string.app_name));
                    onShareClick(sb.toString());
                }
            });


            websiteLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebsite(contest.getUrl());
                }
            });



            secProgressBar.setStartAngle(180);
            secProgressBar.setRingRadiusRatio(0.75f);
            secProgressBar.setTextColor(Color.WHITE);
            secProgressBar.setStyle(CustomProgressBar.Style.REGULAR);// Default style
            secProgressBar.setProgressRingBackgroundColor(Color.TRANSPARENT);
            secProgressBar.setProgressRingForegroundColor("#e300fc");
            secProgressBar.setCenterBackgroundColor("#213051");
            secProgressBar.setVisibility(View.VISIBLE);

            new ShowProgress().execute();
        }

    }

    public void openWebsite(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void onShareClick(String msg) {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(msg)
                .getIntent(), getString(R.string.action_share)));
    }

    public void onError(String msg) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, Html.fromHtml(msg), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        snackbar.show();
    }

    class ShowProgress extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int i = 0;
            while (true) {
                publishProgress(i);
                if (i >= 100) {
                    break;
                }
                i++;
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            daysProgressBar.setProgress(0);
            hoursProgressBar.setProgress(0);
            minsProgressBar.setProgress(0);
            secProgressBar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            daysProgressBar.setProgress(values[0]);
            hoursProgressBar.setProgress(values[0]);
            minsProgressBar.setProgress(values[0]);
            secProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}
