from sixteen.model.symbol import Symbol
from sixteen.util.request import HuoRequest


def test_symbol():
    request = HuoRequest(url='/v1/common/symbols')
    symbols = Symbol.parse_list(request._get_data())
    print(symbols)
