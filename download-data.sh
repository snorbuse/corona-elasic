#!/bin/bash

for X in Deaths Confirmed Recovered; do 
  wget -nv "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-$X.csv" -O time_series_19-covid-$X.csv
done
