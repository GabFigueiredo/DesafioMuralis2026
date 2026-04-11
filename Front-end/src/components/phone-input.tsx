import { PatternFormat } from 'react-number-format';
import { Input } from './ui/input';

export function PhoneInput({ value, onChange }: { value: string; onChange: (value: string) => void }) {
    return <PatternFormat
            format='(##) #####-####'
            customInput={Input}
            placeholder='(99) 99999-9999'
            value={value}
            onValueChange={(value) => onChange(value.value)}
        />;
}
