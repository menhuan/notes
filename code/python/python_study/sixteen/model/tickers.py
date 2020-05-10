import decimal
from dataclasses import dataclass, field

from sixteen.constant import USDT


@dataclass
class MarketTicker:
    open: decimal = field(init=False, default=decimal.Decimal(0))
    close: decimal = field(init=False, default=decimal.Decimal(0))
    low: decimal = field(init=False, default=decimal.Decimal(0))
    high: decimal = field(init=False, default=decimal.Decimal(0))
    amount: int = field(init=False, default=0)
    count: int = field(init=False, default=0)
    vol: int = field(init=False, default=0)
    symbol: str = field(init=False, default=None)
    base_currency: str = field(init=False, default=None)
    quote_currency: str = field(init=False, default=None)
    rise: decimal = field(init=False, default=decimal.Decimal(0))

    def acquire_market_tickers(self, ticker):
        symbol = ticker['symbol']
        if symbol.find('usdt') > 0:
            self.symbol = symbol
            coins = symbol.split('usdt')
            self.base_currency = coins[0]
            self.quote_currency = 'usdt'

        else:
            return False
        self.open = decimal.Decimal(ticker['open'])
        self.close = decimal.Decimal(ticker['close'])
        self.low = decimal.Decimal(ticker['low'])
        self.high = decimal.Decimal(ticker['high'])
        self.amount = ticker['amount']
        self.count = ticker['count']
        self.vol = ticker['vol']
        self.rise = (self.close - self.open) / self.open
        return True
