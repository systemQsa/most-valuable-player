package com.mvp.mostvaluableplayer.service;

import com.mvp.mostvaluableplayer.dto.FileInputData;
import com.mvp.mostvaluableplayer.parser.FileParser;
import com.mvp.mostvaluableplayer.parser.GameParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class ReadFileService {
	private static final String FILE_PATH = "src/main/resources/files";
	private static final String SPLIT_CSV_DATA_REGEX = "\\s*;\\s*";
	private static final String SUFFIX = ".csv";

	@SneakyThrows
	public Map<String, List<FileInputData>> readFiles() {
		Map<String, List<FileInputData>> map = new HashMap<>();


		var isCSVFileFormat = validateFilesFormat();

		if (!isCSVFileFormat) {
			throw new RuntimeException("File format incorrect. Please reload with correct format and try again!");
		}

		Files.walk(Paths.get(FILE_PATH), 2)
			.filter(file -> file.getFileName().toString().endsWith(SUFFIX))
			.map(file -> {
				String line;
				List<FileInputData> gamers = new ArrayList<>();
				FileInputData parsedResult;
				try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH + FileSystems.getDefault()
					.getSeparator() + file.getFileName()))) {
					String gameName = reader.readLine();
					while ((line = reader.readLine()) != null) {
						List<String> data = Arrays.asList(line.split(SPLIT_CSV_DATA_REGEX));
						FileParser parseCommand = GameParser.getParseCommand(gameName);
						parsedResult = parseCommand.parse(data, gameName);
						gamers.add(parsedResult);
					}
					map.put(gameName, gamers);
				} catch (IOException e) {
					log.warn("Failed to open the file", e);
				}
				return gamers;
			})
			.flatMap(Collection::stream)
			.toList();

		return map;
	}

	private boolean validateFilesFormat() throws IOException {
		return Files.walk(Paths.get(FILE_PATH), 2)
			.filter(file -> !Files.isDirectory(file))
			.map(Path::toString)
			.allMatch(file -> file.endsWith(SUFFIX));
	}
}
