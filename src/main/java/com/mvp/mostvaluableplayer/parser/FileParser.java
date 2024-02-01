package com.mvp.mostvaluableplayer.parser;

import com.mvp.mostvaluableplayer.dto.FileInputData;

import java.util.List;

public interface FileParser {

	FileInputData parse(List<String> fileInput, String gameName);
}
