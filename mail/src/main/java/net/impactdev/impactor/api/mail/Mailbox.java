package net.impactdev.impactor.api.mail;

import net.impactdev.impactor.api.mail.filters.MailFilter;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Mailbox {

    Set<MailMessage> mail(MailFilter... filters);

    CompletableFuture<Boolean> append(MailMessage message);

    CompletableFuture<Boolean> remove(MailMessage message);

    CompletableFuture<Boolean> removeIf(MailFilter... filters);

    CompletableFuture<Boolean> clear();

}
