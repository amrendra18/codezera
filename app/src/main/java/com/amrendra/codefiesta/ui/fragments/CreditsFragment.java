package com.amrendra.codefiesta.ui.fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.HtmlUtils;

import butterknife.Bind;

/**
 * Created by amrendrk on 5/15/16.
 */
public class CreditsFragment extends BaseFragment {

    @Bind(R.id.credit_tv)
    TextView creditTv;

    private static StringBuilder sb = new StringBuilder();

    static {
        // clist credit
        sb.append("<p style=\"text-align: justify;\">This app uses ").append(HtmlUtils.getLink("Contest List", "http://clist.by/"));
        sb.append(" API to serve data. A big thanks to ").append(HtmlUtils.getBold("Contest List")).append(". ");
        sb.append("App is freely available and project is open sourced. Please feel free to contribute to it. ");
        sb.append("Check git project ").append(HtmlUtils.getLink("here", AppUtils.GIT_URL)).append(".</p>");

        // platforms
        sb.append("<p style=\"text-align: justify;\">A note of thanks to all online coding platforms for doing a wonderful job in promoting programming culture worldwide. ");
        sb.append("App uses logo icons of few platforms; ");
        sb.append(HtmlUtils.getLink("Codeforces", "http://codeforces.com/")).append(", ");
        sb.append(HtmlUtils.getLink("TopCoder", "https://www.topcoder.com/")).append(", ");
        sb.append(HtmlUtils.getLink("CodeChef", "https://www.codechef.com/")).append(", ");
        sb.append(HtmlUtils.getLink("HackerRank", "https://www.hackerrank.com/")).append(", ");
        sb.append(HtmlUtils.getLink("HackerEarth", "https://www.hackerearth.com/")).append(", ");
        sb.append(HtmlUtils.getLink("Kaggle", "https://www.kaggle.com")).append(", ");
        sb.append(HtmlUtils.getLink("SPOJ", "http://www.spoj.com/")).append(".");
        sb.append("</p>");


        //images
        sb.append("<p style=\"text-align: justify;\">App uses image icons from following resources.<br />");
        sb.append("* Medal icon made by ").append(HtmlUtils.getLink("Madebyoliver", "http://www.flaticon.com/authors/madebyoliver")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/medal_140574")).append(".<br />");
        sb.append("* Send icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/send-symbol_46076")).append(".<br />");
        sb.append("* Play icon made by ").append(HtmlUtils.getLink("Daniel Bruce", "http://www.flaticon.com/authors/daniel-bruce")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/play-button_3690")).append(".<br />");
        sb.append("* Cloud icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/cloud-coding_74907")).append(".<br />");
        sb.append("* Live icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/live-news-report_21617")).append(".<br />");
        sb.append("* Github icon made by ").append(HtmlUtils.getLink("Dave Gandy", "http://www.flaticon.com/authors/dave-gandy")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/github-logo_25231")).append(".<br />");
        sb.append("* Media icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/media-end_31755")).append(".<br />");
        sb.append("* Star icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/star-inside-circle_61935")).append(".<br />");
        sb.append("* Refresh icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/refresh-button_61444")).append(".<br />");
        sb.append("* Nut icon made by ").append(HtmlUtils.getLink("Bogdan Rosu", "http://www.flaticon.com/authors/bogdan-rosu")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/nut-icon_69398")).append(".<br />");
        sb.append("* Share icon made by ").append(HtmlUtils.getLink("Daniel Bruce", "http://www.flaticon.com/authors/daniel-bruce")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/share-connection-sing_3663")).append(".<br />");
        sb.append("* Share icon made by ").append(HtmlUtils.getLink("Plainicon", "http://www.flaticon.com/authors/plainicon")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/share-symbol_76928")).append(".<br />");
        sb.append("* Calendar icon made by ").append(HtmlUtils.getLink("Madebyoliver", "http://www.flaticon.com/authors/madebyoliver")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/calendar_137866")).append(".<br />");
        sb.append("* Calendar icon made by ").append(HtmlUtils.getLink("Madebyoliver", "http://www.flaticon.com/authors/madebyoliver")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/calendar_137863")).append(".<br />");
        sb.append("* Calendar icon made by ").append(HtmlUtils.getLink("Madebyoliver", "http://www.flaticon.com/authors/madebyoliver")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/calendar_137867")).append(".<br />");
        sb.append("* Earth icon made by ").append(HtmlUtils.getLink("Freepik", "http://www.flaticon.com/authors/freepik")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/earth-link_31393")).append(".<br />");
        sb.append("* Notification icon made by ").append(HtmlUtils.getLink("Google", "http://www.flaticon.com/authors/google")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/turn-notifications-off-button_60698")).append(".<br />");
        sb.append("* Bell icon made by ").append(HtmlUtils.getLink("Daniel Bruce", "http://www.flaticon.com/authors/daniel-bruce")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/notification-bell_3513")).append(".<br />");
        sb.append("* Save icon made by ").append(HtmlUtils.getLink("Picol", "http://www.flaticon.com/authors/picol")).append(" from ").append(HtmlUtils.getLink("FlatIcon", "http://www.flaticon.com/free-icon/save-disk_13842")).append(".<br />");
        sb.append("</p>");

        // copyright & infringment
        sb.append("<p style=\"text-align: justify;\">I respect intellectual property of others, and by publishing this app <b>no copyright nor trademark infringement of any sort is intended</b>. If you feel that this app infringes on any of your copyright or trademark, please drop me an ");
        sb.append(HtmlUtils.getLink("email", "mailto:amrendra.nitb+appfeedback@gmail.com")).append(".</p>");

        // disclaimer
        sb.append("<p style=\"text-align: justify;\"><b>Disclaimer:</b><br />");
        sb.append("App tries to keep contests data accurate and fresh, but online coding contests and their schedules are at the discretion of the contest organizers & platform on which they are conducted, and hence there is slight possibility of some unforeseen errors with schedule and timings, hence this app doesn't claim responsibility for accuracy of contests schedules, please be advised to visit contests websites.");
    }

    private static Spanned credits = Html.fromHtml(sb.toString());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        creditTv.setText(credits);
        creditTv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
