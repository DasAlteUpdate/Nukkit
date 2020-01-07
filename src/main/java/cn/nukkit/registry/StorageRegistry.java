package cn.nukkit.registry;

import cn.nukkit.level.provider.LevelProviderFactory;
import cn.nukkit.level.provider.anvil.AnvilProviderFactory;
import cn.nukkit.level.provider.leveldb.LevelDBProviderFactory;
import cn.nukkit.level.storage.StorageType;
import cn.nukkit.level.storage.StorageTypes;
import cn.nukkit.utils.Identifier;
import com.google.common.base.Preconditions;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class StorageRegistry implements Registry {
    private static final StorageRegistry INSTANCE = new StorageRegistry();

    private final Map<Identifier, StorageType> identifiers = new IdentityHashMap<>();
    private final Map<StorageType, LevelProviderFactory> providers = new IdentityHashMap<>();
    private volatile boolean closed;

    private StorageRegistry() {
        this.registerVanillaStorage();
    }

    public static StorageRegistry get() {
        return INSTANCE;
    }

    public synchronized void register(StorageType type, LevelProviderFactory levelProviderFactory)
            throws RegistryException {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(levelProviderFactory, "levelProviderFactory");

        Identifier identifier = type.getIdentifier();
        Preconditions.checkArgument(!this.identifiers.containsKey(identifier));

        this.identifiers.put(identifier, type);
        this.providers.put(type, levelProviderFactory);
    }

    public Optional<StorageType> fromIdentifier(String identifier) {
        return this.fromIdentifier(Identifier.fromString(identifier));
    }

    public Optional<StorageType> fromIdentifier(Identifier identifier) {
        return Optional.ofNullable(this.identifiers.get(identifier));
    }

    public LevelProviderFactory getLevelProviderFactory(StorageType type) {
        Objects.requireNonNull(type, "type");

        return this.providers.get(type);
    }

    @Override
    public void close() {
        Preconditions.checkArgument(!this.closed, "Registry has already been closed");
        this.closed = true;
    }

    private void registerVanillaStorage() throws RegistryException {
        this.register(StorageTypes.ANVIL, AnvilProviderFactory.INSTANCE);
        this.register(StorageTypes.LEVELDB, LevelDBProviderFactory.INSTANCE);
    }
}
