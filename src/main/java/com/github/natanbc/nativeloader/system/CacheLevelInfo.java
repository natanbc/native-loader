package com.github.natanbc.nativeloader.system;

public class CacheLevelInfo {
    private final int level;
    private final CacheType cacheType;
    private final int cacheSize;
    private final int ways;
    private final int lineSize;
    private final int tlbEntries;
    private final int partitioning;
    
    public CacheLevelInfo(int level, CacheType cacheType, int cacheSize, int ways, int lineSize, int tlbEntries, int partitioning) {
        this.level = level;
        this.cacheType = cacheType;
        this.cacheSize = cacheSize;
        this.ways = ways;
        this.lineSize = lineSize;
        this.tlbEntries = tlbEntries;
        this.partitioning = partitioning;
    }
    
    /**
     * @return The level of this cache.
     */
    public int level() {
        return level;
    }
    
    /**
     * @return The type of this cache.
     */
    public CacheType cacheType() {
        return cacheType;
    }
    
    /**
     * @return The size, in bytes, of this cache.
     */
    public int cacheSize() {
        return cacheSize;
    }
    
    /**
     * @return The associativity of this cache. {@code 0} means undefined,
     *         {@code 0xFF} means fully associative.
     */
    public int ways() {
        return ways;
    }
    
    /**
     * @return The size, in bytes, of a cache line.
     */
    public int lineSize() {
        return lineSize;
    }
    
    /**
     * @return The number of entries in the
     *         <a href="https://en.wikipedia.org/wiki/Translation_lookaside_buffer">TLB</a>
     *         of this cache.
     */
    public int tlbEntries() {
        return tlbEntries;
    }
    
    /**
     * @return The number of lines per sector.
     */
    public int partitioning() {
        return partitioning;
    }
    
    @Override
    public String toString() {
        return "CacheLevelInfo{" +
                       "level=" + level +
                       ", cacheType=" + cacheType +
                       ", cacheSize=" + cacheSize +
                       ", ways=" + ways +
                       ", lineSize=" + lineSize +
                       ", tlbEntries=" + tlbEntries +
                       ", partitioning=" + partitioning +
                       '}';
    }
}
