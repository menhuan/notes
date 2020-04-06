import requests
from huobi import RequestClient

from sixteen.constant import HUOBI_API_KEY, HUOBI_SECRET_KEY, BASE_REST_API_URL
from sixteen.model.symbol import Symbol


class HuoRequest:
    proxies = {"http": "http://127.0.0.1:1087", "https": "http://127.0.0.1:1087", }

    def __init__(self, url):
        """
        初始化请求的变量
        """
        self.url = url
        self.request_client = RequestClient(api_key=HUOBI_API_KEY, secret_key=HUOBI_SECRET_KEY)

    def _get_data(self, base_url=BASE_REST_API_URL):
        resp = requests.get(url=BASE_REST_API_URL + self.url, proxies=self.proxies)
        if resp.status_code == 200:
            json_data = resp.json()
            if json_data.get('status') == 'ok':
                return json_data.get('data')
        else:
            raise resp.raise_for_status()


if __name__ == '__main__':
    request = HuoRequest(url='/v1/common/symbols')
    symbols = Symbol.parse_list(request._get_data())
    print(symbols)
