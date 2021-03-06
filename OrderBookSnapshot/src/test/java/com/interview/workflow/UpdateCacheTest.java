package com.interview.workflow;

import com.interview.data.OrderBook;
import com.interview.data.OrderBookCache;
import com.interview.data.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.interview.util.Util.getExchange;

class UpdateCacheTest {

    private IProcess updateCache = new UpdateCache();

    @Test
    public void testUpdateOrderBook() throws Exception {
        Exchange exchange = getExchange();

        //Add to book
        updateCache.execute(exchange);
        Assertions.assertNotNull(OrderBookCache.getInstance().getCacheReference());
        Assertions.assertFalse(OrderBookCache.getInstance().getCacheReference().isEmpty());
        Assertions.assertEquals(2000.0d, getQuantity(exchange));

        exchange.getOrderVO().setStatus(StatusEnum.EXECUTE);
        //Remove from book
        updateCache.execute(exchange);

        Assertions.assertTrue(OrderBookCache.getInstance().getCacheReference()
                .get(exchange.getOrderVO().getExchange()).get(exchange.getOrderVO().getSymbol()).isEmpty());
    }

    private Double getQuantity(Exchange exchange) {
        return OrderBookCache.getInstance().getCacheReference()
                .get(exchange.getOrderVO().getExchange()).get(exchange.getOrderVO().getSymbol())
                .get(OrderBook.OrderKey.getOrderKey(exchange.getOrderVO())).getQuantity().doubleValue();
    }




}