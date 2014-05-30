package net.doepner.file;

/**
 * Media type enumeration
 */
public enum MediaTypeEnum implements MediaType {

    audio("audio"),
    image("images");

    private final String groupingName;

    MediaTypeEnum(String groupingName) {
        this.groupingName = groupingName;
    }

    @Override
    public boolean isAudio() {
        return this == audio;
    }

    @Override
    public boolean isImage() {
        return this == image;
    }

    @Override
    public String getGroupingName() {
        return groupingName;
    }

    @Override
    public Iterable<FileType> getFileTypes() {
        return FileTypeEnum.values(this);
    }
}
