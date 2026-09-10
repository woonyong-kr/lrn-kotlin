# 🟣 lrn-kotlin

Kotlin의 실행 순서와 경계 조건을 작은 프로그램으로 확인하는 공개 학습 저장소입니다. label, 동등성과 hash, lambda 캡처처럼 헷갈리는 동작을 실제 코드와 출력으로 비교합니다.

[Kotlin Wiki](https://docs.woonyong.com/wiki/kotlin/) · [실행 소스](src/main/kotlin/learning/kotlininaction/) · [검사](src/test/kotlin/learning/kotlininaction/)

## 실행

[JDK 21](https://adoptium.net/temurin/releases/?version=21)이 필요합니다. 포함된 Gradle wrapper가 처음 실행할 때 Gradle과 의존성을 내려받으며 Kotlin JVM 2.4.10을 사용합니다.

```sh
./gradlew runChapter02Review
./gradlew test
```

첫 명령은 함수·변수 → property → Color·Expr 분기 → loop·range·collection → exception 순서로 결과를 출력합니다. 한 주제만 실행할 수도 있습니다.

| 명령 | 확인할 동작 |
| --- | --- |
| `./gradlew run` | enum과 여러 형태의 `when` |
| `./gradlew runLabelFlow` | `break@label`, `return@label`, `this@label`의 대상 |
| `./gradlew runRangeProgression` | 포함·제외 경계, `downTo`, `step` |
| `./gradlew runEqualityAndHashing` | HashSet·HashMap, hash 충돌, mutable key |
| `./gradlew runCaptureAndCalls` | 값·참조 캡처, 공유 counter, SAM과 inline |

## 예제와 설계

- [Chapter 2](src/main/kotlin/learning/kotlininaction/chapter02/): 작은 입력의 실행 순서와 정상·실패 결과를 함께 확인합니다.
- [EqualityAndHashing.kt](src/main/kotlin/learning/kotlininaction/chapter04/equality/EqualityAndHashing.kt): 올바른 hash 계약과 의도적으로 깨뜨린 계약을 실제 JVM 컬렉션에서 비교합니다. mutable key는 변경 전에 제거하고 재삽입하는 대조를 제공합니다.
- [CaptureAndCalls.kt](src/main/kotlin/learning/kotlininaction/chapter05/lambdas/CaptureAndCalls.kt): 같은 counter를 두 번, 새 counter를 한 번 호출해 `[1, 2, 1]`을 확인합니다. 함수 객체의 참조 동일성은 compiler·JVM의 관찰 결과이며 할당 횟수 보장이 아닙니다.

예제를 고쳤다면 해당 테스트만 지정할 수 있습니다.

```sh
./gradlew test --tests 'learning.kotlininaction.chapter04.equality.EqualityAndHashingTest'
```

## 현재 범위와 출처

*Kotlin in Action, Second Edition*의 Chapter 2, Chapter 4의 동등성·hash, Chapter 5의 캡처·SAM·inline 예제를 다룹니다. 책 전체 구현은 아닙니다. 레포는 실행 코드·검사, Wiki는 개념·출처·학습 순서를 관리합니다.

[책 공식 예제](https://github.com/Kotlin/kotlin-in-action-2e) · [Kotlin 제어 흐름](https://kotlinlang.org/docs/control-flow.html) · [return과 label](https://kotlinlang.org/docs/returns.html) · [equality](https://kotlinlang.org/docs/equality.html) · [range](https://kotlinlang.org/docs/ranges.html)
