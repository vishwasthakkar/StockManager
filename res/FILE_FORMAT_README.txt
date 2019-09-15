There are portfolio.xml and strategy.xml examples in res folder. 

For persistence part, if a user wants to manually create a portfolio:

1.Between <PortfolioName>ABC</PortfolioName>, replace ABC with the portfolio name you like.
2.<Stock>
        <StockName>GOOG</StockName>
        <BuyDate/>
        <BuyPrice>0.0</BuyPrice>
        <BuyQuantity>100.0</BuyQuantity>
        <CommissionFee>0.0</CommissionFee>
    </Stock>
Here is how to create a stock in this portfolio, between <StockName>GOOG</StockName> is the stock name.
If you add a stock without buying, date would be like this:<BuyDate/>. And price and would be zero:<BuyPrice>0.0</BuyPrice>.
If you buy a stock, buy date would be like this:<BuyDate>Thu Feb 14 00:00:00 EST 2019</BuyDate>.
Between <BuyQuantity>100.0</BuyQuantity>, is the quantity of the stocks.
Between <CommissionFee>0.0</CommissionFee> is the commission fee of this purchase. If you add a stock without buying, this would be zero.
Note: If you the date is invalid or stock market closed at that day, or price didn't match the fact, this file cannot be retrieved successfully. If it is retrieved successfully, then this portfolio will be added to the set of portfolios.

If a user wants to manually create a strategy:
Create like below:
<Strategy>
    <StrategyName>StrategyOne</StrategyName>
    <PortfolioName>ABC</PortfolioName>
    <Amount>2000.0</Amount>
    <CommissionFee>20.0</CommissionFee>
    <StartDate>Thu Feb 14 00:00:00 EST 2019</StartDate>
    <EndDate>Mon Apr 15 00:00:00 EDT 2019</EndDate>
    <Frequency>30</Frequency>
    <StockWeights>
        <StockName>GOOG</StockName>
        <StockWeight>30.0</StockWeight>
    </StockWeights>
    <StockWeights>
        <StockName>AAPL</StockName>
        <StockWeight>70.0</StockWeight>
    </StockWeights>
</Strategy>
1. Replace your StrategyName between <StrategyName> and </StrategyName>, replace your portfolio name between <PortfolioName> and </PortfolioName>, replace your amount between <Amount> and </Amount>, replace your commission fee between <CommissionFee> and </CommissionFee>, replace your start date between <StartDate> and </StartDate>, replace your end date between <EndDate> and </EndDate> and replace your frequency(how many days) between <Frequency> and </Frequency>.
2. Put all your stocks between <StockWeights> and </StockWeights>, and replace StockName and StockWeight.
