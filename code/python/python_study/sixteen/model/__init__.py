import decimal
from dataclasses import dataclass, field


@dataclass
class Kline:
    open: decimal = field(init=False, default=decimal.Decimal(0))
    close: decimal = field(init=False, default=decimal.Decimal(0))
    low: decimal = field(init=False, default=decimal.Decimal(0))
    high: decimal = field(init=False, default=decimal.Decimal(0))
    amount: int = field(init=False, default=0)
    count: int = field(init=False, default=0)
    vol: int = field(init=False, default=0)
    id: int = field(init=False, default=0)

    def build_klines(self, kline):
        self.open = kline['open']
        self.close = kline['close']
        self.low = kline['low']
        self.high = kline['high']
        self.amount = kline['amount']
        self.count = kline['count']
        self.vol = kline['vol']
        self.id = kline['id']


@dataclass
class MarketMerge:
    id: int = field(init=False, default=0)
    ts: int = field(init=False, default=0)
    close: decimal = field(init=False, default=decimal.Decimal(0))
    open: decimal = field(init=False, default=decimal.Decimal(0))
    high: decimal = field(init=False, default=decimal.Decimal(0))
    low: decimal = field(init=False, default=decimal.Decimal(0))
    amount: int = field(init=False, default=0)
    count: int = field(init=False, default=0)
    vol: decimal = field(init=False, default=decimal.Decimal(0))
    ask: list = field(init=False, default_factory=list)
    bid: list = field(init=False, default_factory=list)

    def build_merge(self, merge):
        self.id = merge['id']
        self.close = merge['close']
        self.open = merge['open']
        self.high = merge['high']
        self.low = merge['low']
        self.amount = merge['amount']
        self.count = merge['count']
        self.vol = merge['vol']
        self.ask = merge['ask']
        self.bid = merge['bid']
