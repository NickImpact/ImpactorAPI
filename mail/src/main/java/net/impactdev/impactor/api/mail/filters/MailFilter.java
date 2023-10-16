package net.impactdev.impactor.api.mail.filters;

import net.impactdev.impactor.api.mail.MailMessage;

import java.util.function.Predicate;

@FunctionalInterface
public interface MailFilter extends Predicate<MailMessage> {}

