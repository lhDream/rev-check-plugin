package run.halo.starter

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import run.halo.app.notification.NotificationReasonEmitter
import run.halo.app.core.extension.content.Comment;
import run.halo.app.core.extension.content.Post;
import run.halo.app.core.extension.notification.Subscription
import run.halo.app.notification.NotificationCenter

@Component
open class MarkDownCommentSubject(
    val notificationCenter: NotificationCenter,
) {

    fun subscribeNewCommentOnPostNotification(username: String): Mono<Subscription>? {
        val subscriber = Subscription.Subscriber()
        subscriber.name = username
        val interestReason  = Subscription.InterestReason()
        interestReason.reasonType = "new-comment-on-post"

//        interestReason.expression = "props.repliedOwner == '$username'"

        return notificationCenter.subscribe(subscriber, interestReason)
    }

}