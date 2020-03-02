/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J. If not, see <http://www.gnu.org/licenses/>.
 */

package discord4j.rest.entity;

import discord4j.discordjson.json.ChannelData;
import discord4j.discordjson.json.ChannelModifyRequest;
import discord4j.discordjson.json.MessageCreateRequest;
import discord4j.discordjson.json.MessageData;
import discord4j.rest.RestClient;
import discord4j.rest.util.MultipartRequest;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

public class RestChannel {

    private final RestClient restClient;
    private final long id;

    public RestChannel(RestClient restClient, long id) {
        this.restClient = restClient;
        this.id = id;
    }

    public Mono<ChannelData> getData() {
        return restClient.getChannelService().getChannel(id);
    }

    /**
     * Requests to create a message using a given {@link MessageCreateRequest} as body.
     *
     * @param request request body used to create a new message
     * @return A {@link Mono} where, upon successful completion, emits the created {@link MessageData}. If an
     * error is received, it is emitted through the {@code Mono}.
     */
    public Mono<MessageData> createMessage(MessageCreateRequest request) {
        return restClient.getChannelService().createMessage(id, new MultipartRequest(request));
    }

    /**
     * Requests to create a message using a given {@link MultipartRequest} as body.
     *
     * @param request request body used to create a new message
     * @return A {@link Mono} where, upon successful completion, emits the created {@link MessageData}. If an
     * error is received, it is emitted through the {@code Mono}.
     */
    public Mono<MessageData> createMessage(MultipartRequest request) {
        return restClient.getChannelService().createMessage(id, request);
    }

    /**
     * Requests to edit this text channel using a given {@link ChannelModifyRequest} as body and optionally, a reason.
     *
     * @param request request body used to create a new message
     * @param reason a reason for this action, can be {@code null}
     * @return a {@link Mono} where, upon successful completion, emits the edited {@link ChannelData}. If an error is
     * received, it is emitted through the {@code Mono}.
     */
    public Mono<ChannelData> edit(ChannelModifyRequest request, @Nullable String reason) {
        return restClient.getChannelService().modifyChannel(id, request, reason);
    }

    /**
     * Requests to delete this channel while optionally specifying a reason.
     *
     * @param reason a reason for this action, can be {@code null}
     * @return A {@link Mono} where, upon successful completion, emits nothing; indicating the channel has been deleted.
     * If an error is received, it is emitted through the {@code Mono}.
     */
    public Mono<Void> delete(@Nullable String reason) {
        return restClient.getChannelService().deleteChannel(id, reason).then();
    }
}
