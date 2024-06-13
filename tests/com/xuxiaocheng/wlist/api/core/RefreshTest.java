package com.xuxiaocheng.wlist.api.core;

import com.xuxiaocheng.wlist.api.MainTest;
import com.xuxiaocheng.wlist.api.common.exceptions.TokenExpiredException;
import com.xuxiaocheng.wlist.api.core.files.FileLocation;
import com.xuxiaocheng.wlist.api.core.files.Refresh;
import com.xuxiaocheng.wlist.api.core.files.confirmations.RefreshConfirmation;
import com.xuxiaocheng.wlist.api.core.files.progresses.RefreshProgress;
import com.xuxiaocheng.wlist.api.core.storages.Storage;
import com.xuxiaocheng.wlist.api.core.types.LanzouTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@DisplayName("files - Refresh")
@ExtendWith(Basic.ClientArguments.class)
public abstract class RefreshTest {
    protected long storage;
    protected final long rootStandard;
    protected final long rootEmpty;

    protected RefreshTest(final long rootStandard, final long rootEmpty) {
        super();
        this.rootStandard = rootStandard;
        this.rootEmpty = rootEmpty;
        assert this.rootStandard != this.rootEmpty;
    }

    protected abstract long addStandard(final CoreClient client, final String token);
    protected abstract long addEmpty(final CoreClient client, final String token);

    protected void remove(final CoreClient client, final String token) {
        Basic.get(Storage.remove(client, token, this.storage));
    }

    @BeforeAll
    public static void initialize() {
        MainTest.initialize();
    }
    @AfterAll
    public static void uninitialize() {
        MainTest.uninitialize();
    }

    @BeforeEach
    public void construct(final CoreClient client, final @Basic.CoreToken String token, final TestInfo info) {
        final long storage;
        if (info.getTags().contains("empty"))
            storage = this.addEmpty(client, token);
        else
            storage = this.addStandard(client, token);
        assert storage != 0;
        this.storage = storage;
    }

    @AfterEach
    public void deconstruct(final CoreClient client, final @Basic.CoreToken String token) {
        this.remove(client, token);
        this.storage = 0;
    }

    @Test
    @Tag("empty")
    public void cancel(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation location = new FileLocation(this.storage, this.rootEmpty, true);
        final RefreshConfirmation confirmation = Basic.get(Refresh.refresh(client, token, location));
        Assertions.assertTrue(() -> confirmation.files() == -1 || confirmation.files() == 0);
        Assertions.assertTrue(() -> confirmation.directories() == -1 || confirmation.directories() == 0);

        Basic.thrown(TokenExpiredException.class, Refresh.pause(client, confirmation.token()));
        Basic.thrown(TokenExpiredException.class, Refresh.resume(client, confirmation.token()));
        Basic.thrown(TokenExpiredException.class, Refresh.isPaused(client, confirmation.token()));
        Basic.thrown(TokenExpiredException.class, Refresh.progress(client, confirmation.token()));
        Basic.thrown(TokenExpiredException.class, Refresh.check(client, confirmation.token()));

        Basic.get(Refresh.cancel(client, confirmation.token()));
        Client.close(client);
    }

    public static void refresh(final CoreClient client, final String token, final FileLocation directory) {
        final RefreshConfirmation confirmation = Basic.get(Refresh.refresh(client, token, directory));
        Basic.get(Refresh.confirm(client, confirmation.token()));
        while (true) {
            try {
                final RefreshProgress progress = Refresh.progress(client, confirmation.token()).get();
                Assertions.assertTrue(() -> progress.loadedFiles() <= progress.totalFiles());
                Assertions.assertTrue(() -> progress.loadedDirectories() <= progress.totalDirectories());
//                System.out.println("Progress: " + progress);
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (final ExecutionException exception) {
                if (exception.getCause() instanceof TokenExpiredException)
                    break;
                Assertions.fail(exception);
            } catch (final InterruptedException exception) {
                Assertions.fail(exception);
            }
        }
        Assertions.assertTrue(Basic.get(Refresh.check(client, confirmation.token())));
    }

    @Test
    @Tag("empty")
    @DisplayName("finish: empty")
    public void finishEmpty(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation location = new FileLocation(this.storage, this.rootEmpty, true);
        RefreshTest.refresh(client, token, location);
        Client.close(client);
    }

    @Test
    @DisplayName("finish: standard")
    public void finishStandard(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation location = new FileLocation(this.storage, this.rootStandard, true);
        RefreshTest.refresh(client, token, location);
        Client.close(client);
    }

    @Test
    public void pause(final CoreClient client, final @Basic.CoreToken String token) {
        final FileLocation location = new FileLocation(this.storage, this.rootStandard, true);
        final RefreshConfirmation confirmation = Basic.get(Refresh.refresh(client, token, location));
        Basic.get(Refresh.confirm(client, confirmation.token()));
        Assertions.assertFalse(Basic.get(Refresh.isPaused(client, confirmation.token())));
        Basic.get(Refresh.pause(client, confirmation.token()));

        Assumptions.assumeFalse(Basic.get(Refresh.check(client, confirmation.token())));
        Assertions.assertTrue(Basic.get(Refresh.isPaused(client, confirmation.token())));
        final RefreshProgress progress = Basic.get(Refresh.progress(client, confirmation.token()));
        Assertions.assertDoesNotThrow(() -> TimeUnit.MILLISECONDS.sleep(500));
        Assertions.assertEquals(progress, Basic.get(Refresh.progress(client, confirmation.token())));
        Basic.get(Refresh.resume(client, confirmation.token()));

        Assertions.assertFalse(Basic.get(Refresh.isPaused(client, confirmation.token())));
        Basic.get(Refresh.pause(client, confirmation.token()));

        Basic.get(Refresh.cancel(client, confirmation.token()));
        Client.close(client);
    }

    public static class LanzouRefreshTest extends RefreshTest {
        public LanzouRefreshTest() {
            super(LanzouTest.rootIdStandard, LanzouTest.rootIdEmpty);
        }

        @Override
        protected long addStandard(final CoreClient client, final String token) {
            return LanzouTest.add(client, token, "lanzou-refresh-test", LanzouTest.rootIdStandard);
        }

        @Override
        protected long addEmpty(final CoreClient client, final String token) {
            return LanzouTest.add(client, token, "lanzou-refresh-test-empty", LanzouTest.rootIdEmpty);
        }
    }
}
