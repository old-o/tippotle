package net.doepner.file;

import java.util.Collection;
import java.util.LinkedHashSet;

import static net.doepner.file.MediaTypeEnum.audio;
import static net.doepner.file.MediaTypeEnum.image;

/**
 * File type enumeration
 */
public enum FileTypeEnum implements FileType {

    ogg(audio),
    mp3(audio),
    wav(audio),
    au(audio),

    png(image),
    jpg(image),
    gif(image);

    private final MediaTypeEnum mediaType;

    FileTypeEnum(MediaTypeEnum mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public String getExtension() {
        return name();
    }

    @Override
    public String getMimeType() {
        return mediaType.name() + "/" + name();
    }

    public static Iterable<FileType> values(MediaTypeEnum mediaTypeEnum) {
        final Collection<FileType> result = new LinkedHashSet<>();
        for (FileTypeEnum fileTypeEnum : values()) {
            if (fileTypeEnum.mediaType == mediaTypeEnum) {
                result.add(fileTypeEnum);
            }
        }
        return result;
    }
}
