package br.com.luizalabs.orderfileconverter.boundaries.response

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.util.Date

data class OrderConverterResponse(
    @JsonAlias("user_id")
    val userId: Int,
    val name: String,
    val orders: List<OrderResponse>
)

data class OrderResponse(
    @JsonAlias("order_id")
    val orderId: Int,
    val total: BigDecimal,
    val date: Date,
    val products: List<ProductResponse>
)

data class ProductResponse(
    @JsonAlias("product_id")
    val productId: Int,
    val value: BigDecimal
)

//{
//    "user_id":1,
//    "name":"Zarelli",
//    "orders":[
//    {
//        "order_id":123,
//        "total":"1024.48",
//        "date":"2021-12-01",
//        "products":[
//        {
//            "product_id":111,
//            "value":"512.24"
//        },
//        {
//            "product_id":122,
//            "value":"512.24"
//        }
//        ]
//    }
//    ]
//}