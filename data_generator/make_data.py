from bs4 import BeautifulSoup
import requests
from time import sleep
from tqdm import tqdm
import os
import urllib.parse
import re


from sentence_transformers import SentenceTransformer, InputExample, losses
from torch.utils.data import DataLoader
from datasets import Dataset
from peft import get_peft_model, LoraConfig


stocks_dict = {
    "삼성전자": "005930",
    "SK하이닉스": "000660",
    "포스코DX": "022100",
    "한미반도체": "042700",
    "LG이노텍": "011070",
    "HPSP": "403870",
    "리노공업": "058470",
    "DB하이텍": "000990",
    "이수페타시스": "007660",
    "LX세미콘": "108320",
    "주성엔지니어링": "036930",
    "티씨케이": "064760",
    "파크시스템스": "140860",
    "심텍": "222800",
    "하나머티리얼즈": "166090",
    "해성디에스": "195870",
    "드림텍": "192650",
    "두산테스나": "131970",
    "원익QnC": "074600",
    "비에이치": "090460",
    "넥스틴": "348210",
    "이녹스첨단소재": "272290",
    "피에스케이": "319660",
    "코미코": "183300",
    "HMM": "011200",
    "포스코인터내셔널": "047050",
    "대한항공": "003490",
    "한화에어로스페이스": "012450",
    "현대글로비스": "086280",
    "두산밥캣": "241560",
    "한국항공우주": "047810",
    "한진칼": "180640",
    "HD현대일렉트릭": "267260",
    "팬오션": "028670",
    "LIG넥스원": "079550",
    "에스원": "012750",
    "HD현대인프라코어": "042670",
    "현대엘리베이터": "017800",
    "한전KPS": "051600",
    "에스에프에이": "056190",
    "에코프로에이치엔": "383310",
    "윤성에프앤씨": "372170",
    "경동나비엔": "009450",
    "NICE평가정보": "030190",
    "셀트리온": "068270",
    "한미약품": "128940",
    "클래시스": "214150",
    "케어젠": "214370",
    "메디톡스": "086900",
    "덴티움": "145720",
    "종근당": "185750",
    "파마리서치": "214450",
    "씨젠": "096530",
    "JW중외제약": "001060",
    "동국제약": "086450",
    "엘앤씨바이오": "290650",
    "현대차": "005380",
    "기아": "000270",
    "F&F": "383220",
    "코웨이": "021240",
    "휠라홀딩스": "081660",
    "에스엘": "005850",
    "한세실업": "105630",
    "메가스터디교육": "215200",
    "골프존": "215000",
    "케이카": "381970",
    "쿠쿠홈시스": "284740",
    "신한지주": "055550",
    "삼성화재": "000810",
    "메리츠금융지주": "138040",
    "우리금융지주": "316140",
    "DB손해보험": "005830",
    "미래에셋증권": "006800",
    "한국금융지주": "071050",
    "현대해상": "001450",
    "키움증권": "039490",
    "다우데이타": "032190",
    "고려아연": "010130",
    "한솔케미칼": "014680",
    "솔브레인": "357780",
    "동진쎄미켐": "005290",
    "효성첨단소재": "298050",
    "나노신소재": "121600",
    "효성티앤씨": "298020",
    "동원시스템즈": "014820",
    "TKG휴켐스": "069260",
    "KT&G": "033780",
    "오리온": "271560",
    "BGF리테일": "282330",
    "동서": "026960",
    "오뚜기": "007310",
    "삼양식품": "003230",
    "롯데칠성": "005300",
    "콜마비앤에이치": "200130",
    "엔씨소프트": "036570",
    "JYP Ent.": "035900",
    "에스엠": "041510",
    "제일기획": "030000",
    "SOOP": "067160",
    "S-Oil": "010950"
}


def get_company_info(code: str) -> str:
    url = 'https://finance.naver.com/item/main.naver?code='
    # code = '005930'

    response = requests.get(url + code)
    txt = ''

    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        tags = soup.select('#summary_info > p')        
        for tag in tags:
            txt += tag.text.strip()
    else:
        print(f'({code}) error : ', response.status_code)
    
    return txt

def collect_company_info(stocks: dict):
    for name, code in stocks.items():
        txt = name + '\n'
        txt += get_company_info(code)
        file_route = os.path.join('.\\data\\stock_info', name + '.txt')
        
        if os.path.exists(file_route):
            continue

        with open(file_route, 'w', encoding='utf-8') as f:
            f.write(txt)
            f.close()
            sleep(0.5)



def get_news_url(stocks: dict):
    news_dict = {}

    for name, code in tqdm(stocks.items()):
        url = 'https://finance.naver.com/item/main.naver?code=' + code
        response = requests.get(url)
        news_url = []

        if response.status_code == 200:
            html = response.text
            soup = BeautifulSoup(html, 'html.parser')
            # tags = soup.select("h4.h_sub.sub_tit3 ~ ul li span a")
            # tags = soup.select('#content > div.section.new_bbs > div.sub_section.news_section > ul > li span a[href*="news_read.naver"]')
            tags = soup.select('#content > div.section.new_bbs > div.sub_section.news_section li span a[href*="news_read.naver"]')
            for tag in tags:
                # print(tag['href'])
                news_url.append(tag['href'])
                # break

        news_dict[name] = news_url

    
    return news_dict

def collect_news(news_dict: dict):
    for i, company in enumerate(news_dict):
        print(f'-----{i + 1}/{len(news_dict)}-----')
        print(f'{company} 관련 뉴스 수집 시작')

        txt = ''

        for idx, url in enumerate(news_dict[company]):
            txt += f'\n### news {idx + 1}\n'

            parsed_url = urllib.parse.urlparse(url)
            query_params = urllib.parse.parse_qs(parsed_url.query)

            article_id = query_params.get("article_id", [None])[0]
            office_id = query_params.get("office_id", [None])[0]

            # print("article_id:", article_id)
            # print("office_id:", office_id)
            furl = 'https://n.news.naver.com/mnews/article/' + office_id + '/' + article_id
            # print(furl)
            response = requests.get(furl)
            news_url = []

            if response.status_code == 200:
                html = response.text
                soup = BeautifulSoup(html, 'html.parser')
                tags = soup.select('#dic_area')
                for tag in tags:
                    txt += tag.text
            
            sleep(0.5)
        
        file_route = os.path.join('.\\data\\news', company + '.txt')
        with open(file_route, 'w', encoding='utf-8') as f:
            f.write(txt)
            f.close()







