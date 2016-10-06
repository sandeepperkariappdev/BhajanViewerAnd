package com.sbtso.BhajanViewer;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepperkari on 8/3/16.
 */
public class BhajanXmlParser {

    public static class Date {
        public final String date;
        private Date(String date){
            this.date = date;
        }
    }

    public static class Schedule{
        public final String hostName;
        public final String dateTime;
        public final String location;

        private Schedule(String hostName, String dateTime, String location){
            this.dateTime = dateTime;
            this.hostName = hostName;
            this.location = location;
        }
    }
    public static class Bhajan {
        public final String singerName;
        public final String bhajanText;
        public final String nextSinger;
        public final String nextBhajan;
        public final String bhajanName;

        private Bhajan(String singerName, String bhajanText, String nextSinger, String nextBhajan, String bhajanName) {
            this.singerName = singerName;
            this.bhajanText = bhajanText;
            this.nextSinger = nextSinger;
            this.nextBhajan = nextBhajan;
            this.bhajanName = bhajanName;
        }
    }

    // We don't use namespaces
    private static final String ns = null;

    public List parseBhajan(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Main");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Singer")) {
                entries.add(readEntry(parser));
            }
//            else if(name.equals("Date")){
//
//                entries.add(readDateEntry(parser));
//            }
//            else if (name.equals("Schedules")){
//                entries.add(readSchedulesEntry(parser));
//            }
            else {
                skip(parser);
            }
        }
        return entries;
    }
    private Date readDateEntry(XmlPullParser parser)throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "Date");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Date");
        return new Date(date);
    }
    private Schedule readSchedulesEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Schedules");
        String hostName = null;
        String dateTime = null;
        String location = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Schedule")) {
                hostName = parser.getAttributeValue(null, "HostName");
                dateTime = parser.getAttributeValue(null,"DateTime");
                location = parser.getAttributeValue(null,"Location");
            } else {
                skip(parser);
            }
        }
        return new Schedule(hostName, dateTime, location);
    }
    // Parses the contents of an Bhajan. If it encounters a singerName, bhajanText, nextSinger, nextBhajan or bhajanName, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Bhajan readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Singer");
        String singerName = null;
        String bhajanText = null;
        String nextSinger = null;
        String nextBhajan = null;
        String bhajanName = null;
        if(parser.getName().equals("Singer")){
            bhajanName = parser.getAttributeValue(null,"Name");
        }
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("SingerName")) {
                singerName = readSingerName(parser);
            } else if (name.equals("BhajanText")) {
                bhajanText = readBhajanText(parser);
            } else if (name.equals("NextSinger")) {
                nextSinger = readNextSinger(parser);
            } else if (name.equals("NextBhajan")) {
                nextBhajan = readNextBhajan(parser);
            } else {
                skip(parser);
            }
        }
        return new Bhajan(singerName, bhajanText, nextSinger,nextBhajan, bhajanName);
    }

    // Processes title tags in the feed.
    private String readSingerName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "SingerName");
        String singerName = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "SingerName");
        return singerName;
    }

    // Processes link tags in the feed.
    private String readNextSinger(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "NextSinger");
        String nextSinger = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "NextSinger");
        return nextSinger;
    }
    // Processes link tags in the feed.
    private String readNextBhajan(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "NextBhajan");
        String nextBhajan = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "NextBhajan");
        return nextBhajan;
    }

    // Processes summary tags in the feed.
    private String readBhajanText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "BhajanText");
        String bhajanText = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "BhajanText");
        return bhajanText;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
