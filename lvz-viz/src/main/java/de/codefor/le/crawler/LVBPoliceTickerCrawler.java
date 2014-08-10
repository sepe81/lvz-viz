package de.codefor.le.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import de.codefor.le.model.PoliceTicker;
import de.codefor.le.repositories.PoliceTickerRepository;
import de.codefor.le.utilities.Utils;

/**
 * 
 * @author spinner0815
 * 
 */
@Component
public class LVBPoliceTickerCrawler {

    private static final Logger logger = LoggerFactory.getLogger(LVBPoliceTickerCrawler.class);

    private List<String> crawledNews = new ArrayList<>();

    @Autowired
    PoliceTickerRepository policeTickerRepository;
    private boolean crawlMore = true;

    @Async
    public Future<List<String>> execute(int page) {
        crawledNews = new ArrayList<>();
        try {
            crawlMore = crawlPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("page {} crawled", page);
        logger.debug("crawling of the mainpage done");
        return new AsyncResult<List<String>>(crawledNews);
    }

    /**
     * 
     * @param page
     *            the page which to crawl
     * @return true if all content of the current page is new. Hint for also carwling the next site
     * @throws IOException
     *             if there are problems while writing the detail links to a file
     */
    private boolean crawlPage(int page) throws IOException {
        boolean result = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("http://www.lvz-online.de/leipzig/polizeiticker/polizeiticker-leipzig/r-polizeiticker-leipzig-seite-");
        stringBuilder.append(page);
        stringBuilder.append(".html");
        // read everytime the file for getting all inserted links: already exists check
        String url = stringBuilder.toString();
        logger.info("url: {}", url);
        Document doc = Jsoup.connect(url).userAgent("leipzig crawler").get();
        for (Element e : doc.select("a:contains(mehr...)")) {
            String detailLink = "http://www.lvz-online.de" + e.attr("href");
            String articleId = Utils.getArticleId(detailLink);
            if (!articleId.isEmpty()) {
                List<PoliceTicker> findByArticleId = policeTickerRepository.findByArticleId(articleId);
                if (findByArticleId == null || findByArticleId.isEmpty()) {
                    logger.info("article not stored yet {}", detailLink);
                    crawledNews.add(detailLink);
                } else {
                    logger.info("article allready stored {}", detailLink);
                    result = false;
                }
            }
        }
        return result;
    }

    public void setMoreToCrawl(boolean moreToCrawl) {
        this.crawlMore = moreToCrawl;
    }

    public boolean isMoreToCrawl() {
        return crawlMore;
    }

}
