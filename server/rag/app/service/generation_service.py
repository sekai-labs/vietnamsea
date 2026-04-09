from langchain_core.messages import HumanMessage

from app.client.llm_client import get_llm
from app.prompt.prompt_loader import load_prompt_config
from app.service.retrieval_service import RetrievalResult


class GenerationService:
    def generate(self, query: str, evidence: RetrievalResult) -> str:
        prompt = self._build_prompt(query=query, evidence=evidence)
        llm = get_llm()
        response = llm.invoke([HumanMessage(content=prompt)])
        content = getattr(response, "content", "")
        if not isinstance(content, str) or not content.strip():
            raise ValueError("LLM returned empty content")
        return content.strip()

    @staticmethod
    def _build_prompt(query: str, evidence: RetrievalResult) -> str:
        text_context = "\n\n".join(evidence.chunks[:5]) or "No retrieved text context."
        graph_context = "\n".join(f"- {fact}" for fact in evidence.graph_facts[:8])
        if not graph_context:
            graph_context = "- No retrieved graph facts."

        config = load_prompt_config()
        system_instruction = (
            config.get("system", {}).get("instruction")
            or "You are a grounded assistant. Use only retrieved evidence."
        )

        template = config.get("template", {})
        question_part = template.get("user_question", "Question:\n{query}").format(
            query=query
        )
        text_part = template.get(
            "text_context", "Text Evidence:\n{text_context}"
        ).format(text_context=text_context)
        graph_part = template.get(
            "graph_context", "Graph Evidence:\n{graph_context}"
        ).format(graph_context=graph_context)

        return (
            f"{system_instruction}\n\n{question_part}\n\n{text_part}\n\n{graph_part}\n"
        )


generation_service = GenerationService()
