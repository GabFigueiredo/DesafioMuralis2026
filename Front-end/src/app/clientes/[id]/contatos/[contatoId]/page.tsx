import ContactDetails from "@/templates/contatos/contactDetails";

export default function Page({ params }: { params: Promise<{ id: string; contatoId: string }> }) {
  return <ContactDetails params={params} />;
}