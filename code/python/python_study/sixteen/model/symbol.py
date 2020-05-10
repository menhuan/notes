from dataclasses import dataclass


@dataclass
class Symbol:
    base_currency: str
    quote_currency: str
    price_precision: str
    amount_precision: int
    symbol_partition: str
    symbol: str
    state: str
    value_precision: int
    min_order_amt: int
    max_order_amt: int
    min_order_value: float

    @staticmethod
    def parse_list(data):
        return [Symbol(item['base-currency'], item['quote-currency'], item['price-precision'], item['amount-precision'],
                       item['symbol-partition'], item['symbol'], item['state'], item['value-precision'],
                       item['min-order-amt'], item['max-order-amt'], item['min-order-value'])
                for item in data if item['state'] == 'online']
