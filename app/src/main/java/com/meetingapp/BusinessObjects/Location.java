package com.meetingapp.BusinessObjects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Location
{
    public String Address;
    public String City;
    public String State;
    public String FullAddress;
    public String ZipCode;
    public double Latitude;
    public double Longitude;

    public Location(String address, String city, String state, String zipCode)
        {
        this.Address = address;
        this.City = city;
        this.State = state;
        this.ZipCode = zipCode;
        this.setFullAddress();
    }

    public Location(double lat, double lon) {
        this.Latitude = lat;
        this.Longitude = lon;

    }

    public Location (String address, String city, String state, String zipCode, double lat, double lon) {
        this.Address = address;
        this.City = city;
        this.State = state;
        this.ZipCode = zipCode;
        this.Latitude = lat;
        this.Longitude = lon;
        this.setFullAddress();
    }

    private void setFullAddress() {
        FullAddress = Address + " " + City + ", " + State + " " + ZipCode;
    }

    public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            address = address.replaceAll(" ","%20");

            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public  void calculateCoordinates() {

    }

}
