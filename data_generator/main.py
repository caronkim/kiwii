from make_data import *
from make_vector import *
import pandas as pd

if __name__ == '__main__':
    collect_company_info(stocks_dict)
    print('stock info done')

    print('-'*50)
    news_url = get_news_url(stocks_dict)
    print('collect news url done')

    print('-'*50)
    collect_news(news_url)          
    print('collect news done')

    print('-'*50)
    embedding_vectors = make_embedding_vector()
    word_df = pd.DataFrame(embedding_vectors).T
    word_df.reset_index(names=['word'], inplace=True)
    word_df.to_csv('data/word_vectors.csv', index=False)
    print('making embedding vector done')