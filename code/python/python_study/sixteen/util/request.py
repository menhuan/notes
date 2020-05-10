import requests

from sixteen.constant import HUOBI_API_KEY, HUOBI_SECRET_KEY, BASE_REST_API_URL
from sixteen.model.symbol import Symbol

headers = {
    'authority': 'api.huobi.pro',
    'cache-control': 'max-age=0',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36',
    'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'sec-fetch-site': 'none',
    'sec-fetch-mode': 'navigate',
    'sec-fetch-user': '?1',
    'sec-fetch-dest': 'document',
    'accept-language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7',
    'cookie': '__cfduid=db1057858c0d66373f0fa7ebcdc697d281586128898; _ga=GA1.2.74652652.1586217725; __zlcmid=xbj2clUxF5VcsJ; cf_clearance=dfaf6a49ad085aa98317fa6ebef854e180c9b591-1588410900-0-250',
}


class HuoRequest:
    proxies = {"http": "socks5://127.0.0.1:1086", "https": "socks5://127.0.0.1:1086", }

    # proxies = {"http": "oik00ik.online", "https": "oik00ik.online", }

    def __init__(self, url):
        """
        初始化请求的变量
        """
        self.url = url
        #self.request_client = RequestClient(api_key=HUOBI_API_KEY, secret_key=HUOBI_SECRET_KEY)

    def get_data(self, base_url=BASE_REST_API_URL):

        resp = requests.get(url=BASE_REST_API_URL + self.url, headers=headers, proxies=self.proxies)
        if resp.status_code == 200:
            json_data = resp.json()
            if json_data.get('status') == 'ok':
                return json_data.get('data') if json_data.get('data') is not None else json_data["tick"]
        else:
            raise resp.raise_for_status()


if __name__ == '__main__':
    request = HuoRequest(url='/v1/common/symbols')
    symbols = Symbol.parse_list(request._get_data())
    print(symbols)
