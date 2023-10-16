package net.impactdev.impactor.api.mail;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.UUID;

public interface MailMessage {

    UUID uuid();

    UUID sender();

    Component content();

    Instant timestamp();

}
