
# Test instructions

The following are the instructions for completing the test.  ***Please read through them all before starting.***

The idea of this test is to see what you can achieve within a small timeframe. We are not expecting all points below to be covered, but to add as much * **business value** * as possible before thinking of fixing / tidying any existing issues.

### Synopsis

 - **The aim of this project is to output some basic statistical data about stock prices**
 - The Object model for the data has been provided
 - A Stocks feed API has been wired up by a junior programmer to provide incoming example data (further information on Stock API given below)

We would like you to spend 3 hours on this Java Spring Boot project which accomplishes the following:
 
 - Retrieve 7 working days worth of historical data for the 20 companies in the list provided in the code (also referenced below)
   - The API only gets data from weekdays, not weekends.
 - Store the data from each stock symbol for each day into a structure that can be queried.
 - Log out to the display at regular intervals (say every minute) the following stats: 
	 - **Stat 1** : The top 10 highest value stocks by `close` value for the previous 7 working days (in order – largest first, then order by company code in the case of a tie in value)
         - Each company should only appear once in the list, so only consider the highest close value for each company for each day.
         - If there is not enough data to show the top 10 list - an appropriate message should be displayed instead.
	 - **Stat 2** : The top 5 companies that have the greatest overall ***positive*** % change percent in stock value over `any 3 day` period for that company. 
		 - These should be listed in % change order, with the highest % change at the top.  Then ordered by stock symbol in the case of a tie.
           - The change should be considered from the earliest `open` value to the latest `close` value.
		- If there is not enough data to show the top 5 list - an appropriate message should be displayed instead.

### Stretch goals 
If you finish the above and wish to continue, then the following are stretch goals:

- Use Lombok to remove as much boilerplate code as possible
- Replace any code you consider bad practice (with comments)
- Add one or more Rest APIs to retrieve the same data sets that are being logged to the console.

### Important!
- Please do make sure you commit your work into the repo at the end of the task, within the 3 hrs!  It is a good idea to commit several times, at various milestones during the task, so the assessor can see the progress as a series of logical stages.
- If you wish to continue coding beyond the 3 hours as you are enjoying it so much, then fine!  
  - But only commits in the first 3 hours will be considered... give or take a few mins :-) to keep it fair for everyone... 
- If you have any comments / feedback / questions on the requirements, then please feel free to add them into this Readme.md file

### Stock API info

- It uses an api service called polygon.io
- This service is severely rate limited - this is expected, do not be surprised by this!
- We call the endpoint [Daily Open/Close](https://polygon.io/docs/stocks/get_v1_open-close__stocksticker___date) passing the stock symbol and date to fetch for
- This returns json like so :

`{
"afterHours": 322.1,
"close": 325.12,
"from": "2023-01-09",
"high": 326.2,
"low": 322.3,
"open": 324.66,
"preMarket": 324.5,
"status": "OK",
"symbol": "AAPL",
"volume": 26122646
}`

#### Companies and Symbols

```csv
AAPL,Apple Inc
MSFT,Microsoft Corp
GOOG,Alphabet Inc Class C
AMZN,Amazon.Com Inc.
NVDA,Nvidia Corp
TSLA,Tesla Inc
META,Meta Platforms, Inc.
NFLX,Netflix Inc
INTC,Intel Corporation Corp
SBUX,Starbucks Corp
ABNB,Airbnb, Inc.
PYPL,Paypal Holdings Inc
ATVI,Activision Blizzard Inc
MDLZ,Mondelez International Inc
ADP,Automatic Data Processing Inc
REGN,Regeneron Pharmaceuticals Inc
ISRG,Intuitive Surgical Inc
VRTX,Vertex Pharmaceuticals Inc
MU,Micron Technology Inc
MELI,Mercadolibre Inc
```