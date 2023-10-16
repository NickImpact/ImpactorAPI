package net.impactdev.impactor.api.mail;

import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.impactdev.impactor.api.services.Service;
import net.kyori.adventure.text.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MailService extends Service {

    CompletableFuture<Mailbox> mailbox(UUID target);

    default CompletableFuture<Void> send(UUID from, UUID to, String message) {
        return this.send(from, to, Component.text(message));
    }

    CompletableFuture<Void> send(UUID from, UUID to, Component message);

    default CompletableFuture<Void> sendFromServer(UUID target, String message) {
        return this.send(PlatformSource.SERVER_UUID, target, message);
    }

    default CompletableFuture<Void> sendFromServer(UUID target, Component message) {
        return this.send(PlatformSource.SERVER_UUID, target, message);
    }

}
