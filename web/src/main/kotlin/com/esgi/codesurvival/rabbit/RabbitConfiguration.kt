package com.esgi.codesurvival.rabbit

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@EnableAutoConfiguration
@ComponentScan
class RabbitConfiguration {
    companion object {
        final val topicExchangeName = "spring-boot-exchange"

        val queueName = "test_queue"
    }


    @Bean
    fun queue(): Queue? {
        return Queue(queueName, false)
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    fun binding(queue: Queue?, exchange: TopicExchange?): Binding? {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(@Autowired receiver: GameEventReceiver?): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver, "consume")
    }
}

